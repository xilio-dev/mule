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
                hidden:true,
                component: () => import('@/views/dashboard.vue'),
            },]
    },
    {
        path: '/login',
        name: 'Login',
        hidden:true,
        component: () => import('@/views/login/index.vue'),

    },
]
//异步路由 具备角色-权限控制
export const asyncRoutes = [
    {
        path: '/blog',
        name: 'Blog',
        component: DefaultLayout,
        redirect: '/blog',
        //@ts-ignore
        hidden: false,
        meta: {
            title: '博客管理',
            icon: 'lock',
            roles: ['admin', 'editor']
        },
        children: [
            {
                path: 'article',
                name: 'Article',
                hidden:false,
                component: () => import('@/views/dashboard.vue'),
                meta: {
                    title: '文章列表',
                    icon: 'lock',
                    roles: ['admin', 'editor'] // you can set roles in root nav
                },
            },
            {
                path: 'user',
                name: 'User',
                hidden:false,
                component: () => import('@/views/system/role/index.vue'),
                meta: {
                    title: '用户管理',
                    icon: 'lock',
                    roles: ['admin', 'editor'] // you can set roles in root nav
                },
            },

        ]
    },

]
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: constantRoutes
})

export default router
