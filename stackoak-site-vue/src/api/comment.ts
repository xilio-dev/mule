import {request} from '@/utils/request/Axios.ts';

const Api = {
    DIGG: '/comments/digg',
    UN_DIGG: '/comments/undigg',
    LIST: '/comments/list',
};

export function diggComment(data: any) {
    return request.post(Api.DIGG, data)
}

export function unDiggComment(data: any) {
    return request.put(Api.UN_DIGG, data)
}
export function commentList(data: any) {
    return request.post(Api.LIST,data)
}
