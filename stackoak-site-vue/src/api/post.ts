import {request} from '@/utils/request/Axios.ts';

const Api = {
    articleList: '/article/list',
    postAdd: '/article/add',
    UPDATE: '/article/update',
    postDetail: '/article/detail',
    get: '/article/get',
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
export function postDetail(data:any) {
    return request.post(Api.postDetail,data)
}

export function getArticleById(id:string) {
    return request.get(Api.get+'/'+id)
}


