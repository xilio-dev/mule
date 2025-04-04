import router from '@/router/index'
import {usePermissionStore, useUserStore} from './store'
import {message} from "ant-design-vue";
import DefaultLayout from "@/layout/DefaultLayout.vue";

const whiteList = ['/login', '/auth-redirect'] // no redirect whitelist

router.beforeEach(async (to, from, next) => {
    const userStore = useUserStore()
    const usePermission= usePermissionStore()
    router.addRoute({path:'/blog/user/',component:DefaultLayout,name:to.name})
    console.log(router.getRoutes())

    next()

})

router.afterEach(() => {
})
