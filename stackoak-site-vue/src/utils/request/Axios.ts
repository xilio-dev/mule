import axios from "axios";
import {message} from "ant-design-vue";


const baseApi = import.meta.env.VITE_APP_BASE_API;
const instance = axios.create({
    baseURL: baseApi + '/',
    timeout: 1000,
    headers: {'X-Custom-Header': 'foobar'}
});

// 添加请求拦截器
instance.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    return config;
}, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
});

// 添加响应拦截器
instance.interceptors.response.use(function (response) {
    const {code, msg, data} = response.data
    if (code === 1) {
        return data;
    }else {
        message.error(msg);
    }
}, function (error) {
    return Promise.reject(error);
});
export const request = instance

