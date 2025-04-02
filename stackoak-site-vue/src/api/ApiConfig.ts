import type {ApiConfig} from '@/api/https';

// 分组后的 API 配置
export const API = {
    ARTICLE: {
        articleList: {url: '/article/list', method: 'post'},
        postAdd: {url: '/article/add', method: 'post'},
        delete: {url: '/article/del', method: 'post'},
        update: {url: '/article/update', method: 'put'},
        detail: {url: '/article/detail', method: 'post'},
        get: {url: '/article/get/{id}', method: 'get', pathParams: ['id']},
        digg: {url: '/article/digg/{aid}', method: 'post', pathParams: ['aid']},
        unDigg: {url: '/article/undigg/{aid}', method: 'put', pathParams: ['aid']},
        addToFavor: {url: '/article/addToFavor', method: 'post'},
        fromFavorDel: {url: '/article/fromFavorDel', method: 'delete'},
        rankList: {url: '/article/rank-list', method: 'post'},
        listByUser: {url: '/article/list_by_user', method: 'post'},
        getCollectArticle: {url: '/article/get-collect-article', method: 'post'},
        getColumnArticle: {url: '/article/get-column-article', method: 'post'},
        followList: {url: '/article/follow_list', method: 'post'},
        authorArticleList: {url: '/article/author_article_list', method: 'post'},
        getColumnArticleList: {url: '/article/get_column_article_list', method: 'post'},
        getAuthorHotArticleList: {url: '/article/get_author_hot_article_list', method: 'post'},
        articleComprehensiveRank: {url: '/article/rank/comprehensive', method: 'post'},
        singleArticleStatistics: {url: '/article/single_article_statistics', method: 'post'},
    },
    COMMENT: {
        digg: {url: '/comments/digg', method: 'post'},
        unDigg: {url: '/comments/undigg', method: 'put'},
        list: {url: '/comments/list', method: 'post'},
        lists: {url: '/comments/lists', method: 'post'},
        add: {url: '/comments/add', method: 'post'},
        delete: {url: '/comments/del', method: 'post'},
        deletes: {url: '/comments/delete', method: 'post'},
    },
    USER: {
        getUserInfo: {url: '/user/get', method: 'get'},
        getUserDetail: {url: '/user/detail/{userId}', method: 'get', pathParams: ['userId']},
        follows: {url: '/user/follows', method: 'post'},
        fans: {url: '/user/fans', method: 'post'},
        updateProfile: {url: '/user/update-profile', method: 'put'},
        getUserProfile: {url: '/user/get-profile', method: 'get'},
        followUser: {url: '/user/follow', method: 'post'},
        unFollowUser: {url: '/user/unfollow', method: 'put'},
        updateCover: {url: '/user/update_cover', method: 'put'},
    },
    FILE: {
        uploadImage: {url: '/file/upload', method: 'post'}
    },
    COLLECT: {
        list: {url: '/collect/list', method: 'post'},
        visit_collect: {url: '/collect/visit_collect', method: 'post'},
        get: {url: '/collect/get', method: 'get'},
        del: {url: '/collect/del', method: 'delete'},
        save: {url: '/collect/save', method: 'post'},
        add_article_to_collect: {url: '/collect/add_article_to_collect', method: 'post'},
        remove_article_from_collect: {url: '/collect/remove_article_from_collect', method: 'put'},
    },
    RECOMMEND: {
        author: {url: '/recommend/author', method: 'post'},
        similarityArticle: {url: '/recommend/similarity-article', method: 'post'},
    },
    REPORT: {
        createReport: {url: '/report/createReport', method: 'post'},
    },
    THEME_PHOTO: {
        list: {url: '/theme_photo/list', method: 'post'},
    },
    COLUMN: {
        subscribe: {url: '/column/subscribe', method: 'post'},
        cancelSubscribe: {url: '/column/cancel_subscribe', method: 'put'},
    },
    SEARCH_HISTORY: {
        delSearchHis: {url: '/del_search_his', method: 'delete'},
    },
    ANNOUNCEMENT: {
        list: {url: '/announcement/list', method: 'post'},
        type: {url: '/announcement/type', method: 'post'},
        detail: {url: '/announcement/detail/{type}', method: 'get', pathParams: ['type']},
    },
    MATERIAL: {
        list: {url: '/material/list', method: 'post'},
        user: {url: '/material/user', method: 'post'},
    }
} as const;

export type ApiGroup = typeof API;
