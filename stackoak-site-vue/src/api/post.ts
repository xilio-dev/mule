import {request} from '@/utils/request/Axios.ts';

const Api = {
    articleList: '/article/list',
    postAdd: '/article/add',
    UPDATE: '/article/update',
    postDetail: '/article/detail',
    get: '/article/get',
    digg: '/article/digg',
    UN_DIGG: '/article/undigg',

};

export function articleList(query: any) {
    return request.post(Api.articleList, query)
}

export function addArticle(article: any) {
    return request.post(Api.postAdd, article)
}

export function updateArticle(article: any) {
    return request.put(Api.UPDATE, article)
}

export function postDetail(data: any) {
    return request.post(Api.postDetail, data)
}

export function getArticleById(id: string) {
    return request.get(Api.get + '/' + id)
}

export function diggArticle(data: any) {
    return request.post(Api.digg, data)
}

export function unDiggArticle(data: any) {
    return request.put(Api.UN_DIGG, data)
}
