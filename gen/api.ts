import {request} from '@/utils/request/Axios.ts';

const Api = {
    postList: '/post/postList',
    postAdd: '/post/postAdd',
    postDetail: '/post/postDetail',
};
export function postList(query: any) {
    return request.post(Api.postList, query)
}
export function postAdd(query: any) {
    return request.post(Api.postAdd, query)
}
export function postDetail(query: any) {
    return request.post(Api.postDetail, query)
}

