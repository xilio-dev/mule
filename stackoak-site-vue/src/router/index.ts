import {createRouter, createWebHistory, createWebHashHistory} from 'vue-router'

const router = createRouter({
    history: createWebHashHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'Home',
            component: () => import('@/views/HomeView.vue'),
        },
        {
            path: '/login',
            name: 'login',
            component: () => import('@/views/login.vue'),
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
