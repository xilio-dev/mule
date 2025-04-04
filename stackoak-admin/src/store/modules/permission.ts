import {defineStore} from "pinia";
import {ref} from "vue";
import {constantRoutes, asyncRoutes} from "@/router";
import {API} from "@/api/ApiConfig.ts";
import {Https} from "@/utils/Https.ts";
import DefaultLayout from "@/layout/DefaultLayout.vue";
export const loadView = (view:any) => {
    if (process.env.NODE_ENV === 'development') {
        return (resolve) => require([`@/views/${view}`], resolve)
    } else {
        return () => import(`@/views/${view}`)
    }
}
// 遍历后台传来的路由字符串，转换为组件对象
function filterAsyncRouter(asyncRouterMap, lastRouter = false, type = false) {
    return asyncRouterMap.filter(route => {
        if (type && route.children) {
            route.children = filterChildren(route.children)
        }
        if (route.component) {
            if (route.component === 'DefaultLayout') {
                route.component = DefaultLayout
            }  else {
                route.component = loadView(route.component)
            }
        }
        if (route.children != null && route.children && route.children.length) {
            route.children = filterAsyncRouter(route.children, route, type)
        } else {
            delete route['children']
            delete route['redirect']
        }
        return true
    })
}

function filterChildren(childrenMap, lastRouter = false) {
    let children = []
    childrenMap.forEach(el => {
        el.path = lastRouter ? lastRouter.path + '/' + el.path : el.path
        if (el.children && el.children.length && el.component === 'RootLayout') {
            children = children.concat(filterChildren(el.children, el))
        } else {
            children.push(el)
        }
    })
    return children
}
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
            Https.action(API.MENU.getRoutes).then(res=>{
                let accessedRoutes
                if (roles.includes('admin')) {
                    accessedRoutes = asyncRoutes || []
                } else {
                    accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
                }

                let cusRoutes= filterAsyncRoutes(res, roles)
                accessedRoutes = constantRoutes.concat(accessedRoutes);
                accessedRoutes= accessedRoutes.concat(cusRoutes)
                accessedRoutes=filterAsyncRouter(accessedRoutes, false, true)
                permission.value.addRoutes = accessedRoutes
                permission.value.routes = constantRoutes.concat(accessedRoutes)
                resolve(permission.value.routes);
            })
        })
    }

    return {permission, generateRoutes}
},{
    persist: true // 启用持久化，默认存储到 localStorage
});
