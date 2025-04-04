import {defineStore} from "pinia";
import {ref} from "vue";
import {constantRoutes, asyncRoutes} from "@/router";
//判断是否具有某个角色权限
const hasPermission = (roles: any, route: any) => {
    if (route.meta && route.meta.roles) {
        return roles.some((role: any) => route.meta.roles.includes(role))
    } else {
        return true
    }
}
//过滤掉不具备某个角色权限的路由
export const filterAsyncRoutes = (routes: any, roles: any) => {
    const res = <any>[]
    routes.forEach((route: any) => {
        const tmp = {...route}
        if (hasPermission(roles, tmp)) {
            if (tmp.children) {
                tmp.children = filterAsyncRoutes(tmp.children, roles)
            }
            res.push(tmp)
        }
    })

    return res
}
export const usePermissionStore = defineStore('permission', () => {
    // 使用 UserInfo 接口定义 userinfo 的类型
    const permission = ref({
        routes: <any>[],//所有路由
        addRoutes: <any>[] //添加的动态路由
    });
    //根据角色生成路由
    function generateRoutes(roles: string[]) {
        return new Promise(resolve => {
            let accessedRoutes
            if (roles.includes('admin')) {
                accessedRoutes = asyncRoutes || []
            } else {
                accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
            }
            accessedRoutes = constantRoutes.concat(accessedRoutes);
            permission.value.addRoutes = accessedRoutes
            permission.value.routes = constantRoutes.concat(accessedRoutes)
            resolve(accessedRoutes);
        })
    }

    return {permission, generateRoutes}
});
