import {request} from '@/utils/request/Axios.ts';

const Api = {
    ARTICLE_SEARCH: '/search',
    GET_SEARCH_HIS: '/get_search_his',
};

export function searchArticle(data:any) {
    return request.post(Api.ARTICLE_SEARCH, data)
}
export function getSearchHistory() {
    return request.get(Api.GET_SEARCH_HIS)
}
