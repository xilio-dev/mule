/**
 * @description: API统一配置
 */
export const API = {
    USER: {
        get: {url: '/user/get', method: 'get'},
        login: {url: '/user/login', method: 'post'},
    },
    ROLE: {},
    ARTICLE: {},
    REPORT: {},
    MENU: {
        getRoutes: {url: '/menu/get-routes', method: 'get'},
    }
} as const;

export type ApiGroup = typeof API;
