import {createRouter, createWebHistory} from 'vue-router'
import DefaultLayout from "@/layout/DefaultLayout.vue";
//常量路由
export const constantRoutes = [
    {
        path: '/',
        name: 'Layout',
        component: DefaultLayout,
        redirect: '/dashboard',
        //@ts-ignore
        hidden: true,

        children: [
            {
                path: 'dashboard',
                name: 'dashboard',
                component: () => import('@/views/dashboard.vue'),
            },]
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/views/login/index.vue'),

    },
]
//异步路由 具备角色-权限控制
export const asyncRoutes = [
    {

    },
]
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: constantRoutes
})

export default router
