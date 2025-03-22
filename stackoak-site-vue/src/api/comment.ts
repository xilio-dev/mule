import {request} from '@/utils/request/Axios.ts';

const Api = {
    DIGG: '/comments/digg',
    UN_DIGG: '/comments/undigg',
    LIST: '/comments/list',
    LISTS: '/comments/lists',
    ADD: '/comments/add',
    DEL: '/comments/del',
};

export function diggComment(data: any) {
    return request.post(Api.DIGG, data)
}

export function unDiggComment(data: any) {
    return request.put(Api.UN_DIGG, data)
}

export function commentList(data: any) {
    return request.post(Api.LIST, data)
}
export function commentListS(data: any) {
    return request.post(Api.LISTS, data)
}

export function addComment(data: any) {
    return request.post(Api.ADD, data)
}

export function deleteComment(data: any) {
    return request.post(Api.DEL, data)
}
