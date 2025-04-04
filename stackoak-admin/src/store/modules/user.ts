import {defineStore} from "pinia";
import {reactive, ref} from "vue";

import {API} from "@/api/ApiConfig.ts";
import {Https} from "@/utils/Https.ts";
import {usePermissionStore} from "@/store";

export const useUserStore = defineStore('user', () => {
    // 使用 UserInfo 接口定义 userinfo 的类型
    const user = reactive({
        id: '',
        nickname: '',
        username: '',
        avatar: '',
        role: [],
        permission: []
    });
    const loginAction = (loginParam:any) => {
        return new Promise((resolve, reject) => {
            const {username, password} = loginParam
            Https.action(API.USER.login, {username:username, password:password}).then((res: any) => {

                const {role} = res
               const usePermission = usePermissionStore()
                usePermission.generateRoutes(role)// 生成路由
                resolve(res)
            }).catch(err=>{
                reject(err)
            })
        })
    }
    return {user, loginAction}
},{
    persist: true // 启用持久化，默认存储到 localStorage
});
