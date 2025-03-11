import {request} from '@/utils/request/Axios.ts';

const Api = {
    GET_SYS_CONFIG: '/config/get-sys-config',
    GET_USER_CONFIG: '/config/get-user-config',
};

export function getSysConfigInfo() {
    return request.get(Api.GET_SYS_CONFIG)
}
export function getUserConfigInfo() {
    return request.get(Api.GET_USER_CONFIG)
}
