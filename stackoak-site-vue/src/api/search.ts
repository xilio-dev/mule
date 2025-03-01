import {request} from '@/utils/request/Axios.ts';

const Api = {
    ARTICLE_SEARCH: '/search',
};

export function searchArticle(data:any) {
    return request.post(Api.ARTICLE_SEARCH, data)
}
