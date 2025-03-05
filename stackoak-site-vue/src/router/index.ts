import {createRouter, createWebHistory, createWebHashHistory} from 'vue-router'
// createWebHistory 模式URL不会显示#号
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'Home',
            component: () => import('@/views/HomeView.vue'),
        },
        {
            path: '/setting',
            name: 'Setting',
            component: () => import('@/layouts/SettingLayout.vue'),
            redirect: '/setting/profile',
            children: [
                {
                    path: 'profile',
                    name: 'Profile',
                    component: () => import('@/views/setting/profile/index.vue'),
                },
                {
                    path: 'account',
                    name: 'Account',
                    component: () => import('@/views/setting/account/index.vue'),
                },
                {
                    path: 'notification',
                    name: 'Notification',
                    component: () => import('@/views/setting/notification/index.vue'),
                },
                {
                    path: 'black-list',
                    name: 'Black-list',
                    component: () => import('@/views/setting/black-list/index.vue'),
                },
                {
                    path: 'privacy',
                    name: 'Privacy',
                    component: () => import('@/views/setting/privacy/index.vue'),
                }
            ]

        },

        {
            path: '/login',
            name: 'login',
            component: () => import('@/views/login.vue'),
        },
        {
            path: '/search',
            name: 'search',
            component: () => import('@/views/search/index.vue'),
        },

        {
            path: '/test',
            name: 'login',
            component: () => import('@/views/test.vue'),
        },
        {
            path: '/qus',
            name: 'qus',
            component: () => import('@/views/question/index.vue'),
        },
        {
            path: '/creator',
            name: 'creator',
            component: () => import('@/views/creator/Index.vue'),
        },
        {
            path: '/author',
            name: 'author',
            component: () => import('@/views/author/Index.vue'),
        },
        {
            path: '/editor',
            name: 'editor',
            component: () => import('@/views/editor/Index.vue'),
        },
        {
            path: '/post/:id',
            name: 'post',
            component: () => import('@/views/post/Index.vue'),
        },
        {
            path: '/resource',
            name: 'resource',
            component: () => import('@/views/resource/Index.vue'),
        },
        {
            path: '/opensource/document',
            name: 'document',
            component: () => import('@/views/opensource/document/Index.vue'),
        },
        {
            path: '/opensource/document/detail',
            name: 'document-detail',
            component: () => import('@/views/opensource/document/detail/Index.vue'),
        },
        {
            path: '/opensource/project',
            name: 'project',
            component: () => import('@/views/opensource/project/Index.vue'),
        },
        {
            path: '/login',
            name: 'login',
            component: () => import('@/views/login/Index.vue'),
        },
        {
            path: '/chat',
            name: 'chat',
            component: () => import('@/views/chat/index.vue'),
        },
        {
            path: '/msg',
            name: 'msg',
            component: () => import('@/views/msg/index.vue'),
        },
    ],
})

export default router
