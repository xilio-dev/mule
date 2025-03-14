import {request} from '@/utils/request/Axios.ts';

const Api = {
    articleList: '/article/list',
    postAdd: '/article/add',
    DELETE: '/article/del',
    UPDATE: '/article/update',
    postDetail: '/article/detail',
    get: '/article/get',
    digg: '/article/digg',
    UN_DIGG: '/article/undigg',
    ADD_TO_FAVOR: '/article/addToFavor',
    FROM_FAVOR_DEL: '/article/fromFavorDel',
    RANK_IST: '/article/rank-list',
    LIST_BY_USER: '/article/list_by_user',

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
export function deleteArticle(aid: string) {
    return request.post(Api.DELETE, aid)
}


export function postDetail(data: any) {
    return request.post(Api.postDetail, data)
}
export function articleListByUser(query: any) {
    return request.post(Api.LIST_BY_USER, query)
}

export function getArticleById(id: string) {
    return request.get(Api.get + '/' + id)
}

export function diggArticle(data: any) {
    return request.post(Api.digg, data)
}

export function unDiggArticle(data: any) {
    return request.delete(Api.UN_DIGG, data)
}

export function addToFavor(data: any) {
    return request.post(Api.ADD_TO_FAVOR, data)
}

export function fromFavorDel(data: any) {
    return request.delete(Api.FROM_FAVOR_DEL, data)
}

export function authorArticleRank(query: any) {
    return request.post(Api.RANK_IST, query)
}
