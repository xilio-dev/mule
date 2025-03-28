
import { request } from '@/utils/request/Axios.ts';
// 定义 HTTP 方法类型
export type HttpMethod = 'get' | 'post' | 'put' | 'delete';

// 接口配置类型，支持泛型
export interface ApiConfig {
  url: string;
  method: HttpMethod;
  pathParams?: string[]; // 支持路径参数
}

// 请求工具的类型定义
interface Request {
  get: <T = any>(url: string, params?: any) => Promise<T>;
  post: <T = any>(url: string, data?: any) => Promise<T>;
  put: <T = any>(url: string, data?: any) => Promise<T>;
  delete: <T = any>(url: string, params?: any) => Promise<T>;
}
// 实现 Request 接口
export const request2: Request = {
  get: (url, params) => request.get(url, { params }),
  post: (url, data) => request.post(url, data),
  put: (url, data) => request.put(url, data),
  delete: (url, params) => request.delete(url, { params }),
};

// Https 对象，支持泛型
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

    const methodMap: Record<HttpMethod, (url: string, data?: any) => Promise<T>> = {
      get: request2.get,
      post: request2.post,
      put: request2.put,
      delete: request2.delete,
    };

    const requestMethod = methodMap[apiConfig.method];
    if (!requestMethod) {
      throw new Error(`Unsupported method: ${apiConfig.method}`);
    }

    return requestMethod(url, data);
  },
};
