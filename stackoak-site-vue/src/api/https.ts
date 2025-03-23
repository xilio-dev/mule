import { request } from '@/utils/request/Axios.ts';

// 定义 HTTP 方法类型
export type HttpMethod = 'get' | 'post' | 'put' | 'delete';

// 接口配置类型
export interface ApiConfig {
    url: string;
    method: HttpMethod;
    pathParams?: string[]; // 支持路径参数
}

// Https 对象
export const Https = {
    action: <T = any>(apiConfig: ApiConfig, data?: any): Promise<T> => {
        let url = apiConfig.url;

        // 处理路径参数
        if (apiConfig.pathParams && data) {
            apiConfig.pathParams.forEach((param) => {
                if (data[param]) {
                    url = url.replace(`{${param}}`, data[param]);
                    delete data[param]; // 删除已用作路径参数的字段
                } else {
                    throw new Error(`Missing path parameter: ${param} for ${url}`);
                }
            });
        }

        // 使用映射替代 switch，提高可维护性和类型安全性
        const methodMap: Record<HttpMethod, (url: string, data?: any) => Promise<T>> = {
            get: request.get,
            post: request.post,
            put: request.put,
            delete: request.delete,
        };

        const requestMethod = methodMap[apiConfig.method];
        if (!requestMethod) {
            throw new Error(`Unsupported method: ${apiConfig.method}`);
        }

        return requestMethod(url, data);
    },
};
