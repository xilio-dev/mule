import type {ApiConfig} from '@/api/https';

// 分组后的 API 配置
export const API = {
    ARTICLE: {
        articleList: {url: '/article/list', method: 'post'} as ApiConfig,
        postAdd: {url: '/article/add', method: 'post'} as ApiConfig,
        delete: {url: '/article/del', method: 'post'} as ApiConfig,
        update: {url: '/article/update', method: 'put'} as ApiConfig,
        detail: {url: '/article/detail', method: 'post'} as ApiConfig,
        get: {url: '/article/get/{id}', method: 'get', pathParams: ['id']} as ApiConfig,
        digg: {url: '/article/digg/{aid}', method: 'post', pathParams: ['aid']} as ApiConfig,
        unDigg: {url: '/article/undigg/{aid}', method: 'put', pathParams: ['aid']} as ApiConfig,
        addToFavor: {url: '/article/addToFavor', method: 'post'} as ApiConfig,
        fromFavorDel: {url: '/article/fromFavorDel', method: 'delete'} as ApiConfig,
        rankList: {url: '/article/rank-list', method: 'post'} as ApiConfig,
        listByUser: {url: '/article/list_by_user', method: 'post'} as ApiConfig,
        getCollectArticle: {url: '/article/get-collect-article', method: 'post'} as ApiConfig,
        getColumnArticle: {url: '/article/get-column-article', method: 'post'} as ApiConfig,
        followList: {url: '/article/follow_list', method: 'post'} as ApiConfig,
        authorArticleList: {url: '/article/author_article_list', method: 'post'} as ApiConfig,
        getColumnArticleList: {url: '/article/get_column_article_list', method: 'post'} as ApiConfig,
        getAuthorHotArticleList: {url: '/article/get_author_hot_article_list', method: 'post'} as ApiConfig,
        articleComprehensiveRank: {url: '/article/rank/comprehensive', method: 'post'} as ApiConfig,
        singleArticleStatistics: {url: '/article/single_article_statistics', method: 'post'} as ApiConfig,
    },
    COMMENT: {
        digg: {url: '/comments/digg', method: 'post'} as ApiConfig,
        unDigg: {url: '/comments/undigg', method: 'put'} as ApiConfig,
        list: {url: '/comments/list', method: 'post'} as ApiConfig,
        lists: {url: '/comments/lists', method: 'post'} as ApiConfig,
        add: {url: '/comments/add', method: 'post'} as ApiConfig,
        delete: {url: '/comments/del', method: 'post'} as ApiConfig,
        deletes: {url: '/comments/delete', method: 'post'} as ApiConfig,
    },
    USER: {
        getUserInfo: {url: '/user/get', method: 'get'} as ApiConfig,
        getUserDetail: {url: '/user/detail/{userId}', method: 'get', pathParams: ['userId']} as ApiConfig,
        follows: {url: '/user/follows', method: 'post'} as ApiConfig,
        fans: {url: '/user/fans', method: 'post'} as ApiConfig,
        updateProfile: {url: '/user/update-profile', method: 'put'} as ApiConfig,
        getUserProfile: {url: '/user/get-profile', method: 'get'} as ApiConfig,
        followUser: {url: '/user/follow', method: 'post'} as ApiConfig,
        unFollowUser: {url: '/user/unfollow', method: 'put'} as ApiConfig,
        updateCover: {url: '/user/update_cover', method: 'put'} as ApiConfig,
    },
    FILE: {
        uploadImage: {url: '/file/upload', method: 'post'} as ApiConfig
    },
    COLLECT: {
        list: {url: '/collect/list', method: 'post'} as ApiConfig,
        visit_collect: {url: '/collect/visit_collect', method: 'post'} as ApiConfig,
        get: {url: '/collect/get', method: 'get'} as ApiConfig,
        del: {url: '/collect/del', method: 'delete'} as ApiConfig,
        save: {url: '/collect/save', method: 'post'} as ApiConfig,
        add_article_to_collect: {url: '/collect/add_article_to_collect', method: 'post'} as ApiConfig,
        remove_article_from_collect: {url: '/collect/remove_article_from_collect', method: 'put'} as ApiConfig,
    },
    RECOMMEND: {
        author: {url: '/recommend/author', method: 'post'} as ApiConfig,
    },
    REPORT: {
        createReport: {url: '/report/createReport', method: 'post'} as ApiConfig,
    },
    THEME_PHOTO: {
        list: {url: '/theme_photo/list', method: 'post'} as ApiConfig,
    },
    COLUMN: {
        subscribe: {url: '/column/subscribe', method: 'post'} as ApiConfig,
        cancelSubscribe: {url: '/column/cancel_subscribe', method: 'put'} as ApiConfig,
    },
    SEARCH_HISTORY: {
        delSearchHis: {url: '/del_search_his', method: 'delete'} as ApiConfig,
    },
    ANNOUNCEMENT: {
        list: {url: '/announcement/list', method: 'post'} as ApiConfig,
        type: {url: '/announcement/type', method: 'post'} as ApiConfig,
        detail: {url: '/announcement/detail/{type}', method: 'get', pathParams: ['type']} as ApiConfig,
    },
    MATERIAL: {
        list: {url: '/material/list', method: 'post'},
        user: {url: '/material/user', method: 'post'},
    }
} as const;

export type ApiGroup = typeof API;
