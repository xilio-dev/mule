import {defineStore} from "pinia";
import {ref} from "vue";

export const usePermissionStore = defineStore('permission', () => {
    // 使用 UserInfo 接口定义 userinfo 的类型
    const permission = ref({
        routes: [],
        addRoutes: []
    });

    return {permission};
});
