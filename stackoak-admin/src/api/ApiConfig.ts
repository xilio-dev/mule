import type {ApiConfig} from '@/utils/Https.ts';

// 分组后的 API 配置
export const API = {
    SYS_USER: {
        get: {url: '/user/get', method: 'get'} as ApiConfig,
    },
    ROLE: {},
    MENU: {},
    ARTICLE: {},
    REPORT: {},
} as const;

export type ApiGroup = typeof API;
