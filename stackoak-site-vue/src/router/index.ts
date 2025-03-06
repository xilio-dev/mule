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
            path: '/creator',
            name: 'Creator',
            component: () => import('@/layouts/CreatorLayout.vue'),
            redirect: '/creator/index',
            children: [
                {
                    path: 'index',
                    name: 'Index',
                    component: () => import('@/views/creator/index.vue'),
                },
                {
                    path: 'content/article',
                    name: 'ContentArticle',
                    component: () => import('@/views/creator/content/article/index.vue'),
                },
                {
                    path: 'content/opensource',
                    name: 'ContentOpensource',
                    component: () => import('@/views/creator/content/opensource/index.vue'),
                },
                {
                    path: 'content/column',
                    name: 'ContentColumn',
                    component: () => import('@/views/creator/content/column/index.vue'),
                },
                {
                    path: 'content/comment',
                    name: 'ContentComment',
                    component: () => import('@/views/creator/content/comment/index.vue'),
                },

                {
                    path: 'analysis/articles',
                    name: 'AnalysisArticle',
                    component: () => import('@/views/creator/analysis/article/index.vue'),
                },
                {
                    path: 'analysis/fans',
                    name: 'AnalysisFans',
                    component: () => import('@/views/creator/analysis/fans/index.vue'),
                },
                {
                    path: 'setting/config-blog',
                    name: 'SettingConfigBlog',
                    component: () => import('@/views/creator/settings/config-blog/index.vue'),
                },
                {
                    path: 'tool/export',
                    name: 'ToolExport',
                    component: () => import('@/views/creator/tool/export/index.vue'),
                },
                {
                    path: 'tool/import',
                    name: 'ToolImport',
                    component: () => import('@/views/creator/tool/import/index.vue'),
                },
                {
                    path: 'tool/images',
                    name: 'ToolImages',
                    component: () => import('@/views/creator/tool/resource/images/index.vue'),
                },
            ]
        },
        {
            path: '/msg',
            name: 'Msg',
            component: () => import('@/layouts/MessageLayout.vue'),
            redirect: '/msg/comment',
            children: [
                {
                    path: 'comment',
                    name: 'Comment',
                    component: () => import('@/views/msg/comment/index.vue'),
                },
                {
                    path: 'like',
                    name: 'Like',
                    component: () => import('@/views/msg/like/index.vue'),
                },
                {
                    path: 'attention',
                    name: 'Attention',
                    component: () => import('@/views/msg/attention/index.vue'),
                },
                {
                    path: 'chat',
                    name: 'Chat',
                    component: () => import('@/views/msg/chat/index.vue'),
                },
                {
                    path: 'system',
                    name: 'System',
                    component: () => import('@/views/msg/system/index.vue'),
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
    ],
})

export default router
