import {defineStore} from "pinia";
import {ref} from "vue";
import {sendLogout} from "@/api/auth.ts";

interface UserInfo {
    userId?: number;
    nickname?: string;
    email?: string;
    avatar?: string;
    token?: string;
}

export const useUserStore = defineStore('user', () => {
    // 使用 UserInfo 接口定义 userinfo 的类型
    const userinfo = ref<UserInfo>({
        userId: undefined,
        nickname: undefined,
        email: undefined,
        avatar: undefined,
        token: undefined
    });

    //是否登陆
    function isLogin() {
        return userinfo.value.userId !== undefined
    }

    // 退出登录
    function logout() {
        userinfo.value = {
            userId: undefined,
            nickname: undefined,
            email: undefined,
            avatar: undefined,
        };
        localStorage.removeItem('token')
        localStorage.removeItem('userinfo');
        sendLogout()
    }

    // 定义 setUserInfo 方法，参数类型为 UserInfo
    function setUserInfo(userInfo: UserInfo) {
        userinfo.value = {...userinfo.value, ...userInfo};
        localStorage.setItem('userinfo', JSON.stringify(userInfo))
    }

    function setToken(token: string) {
        userinfo.value = {...userinfo.value, token};
        localStorage.setItem('token', token)
    }
    // 定义 updateEmail 方法，用于更新 email 字段并同步到 localStorage
    function updateEmail(newEmail: string) {
        setUserInfo({ ...userinfo.value, email: newEmail });
    }
    function getToken() {
        localStorage.getItem('token')
    }

    // 获取用户信息
    function getUserInfo(): UserInfo {
        const storedUserInfo = localStorage.getItem('userinfo');
        if (storedUserInfo) {
            return JSON.parse(storedUserInfo);
        }
        return userinfo.value;
    }

    // 初始化时从 localStorage 加载用户信息
    if (localStorage.getItem('userinfo')) {
        userinfo.value = getUserInfo();
    }
    return {userinfo, setUserInfo, logout, getToken, setToken, isLogin,updateEmail};
});
