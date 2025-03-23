import {request} from '@/utils/request/Axios.ts';

const Api = {
    RECOMMEND_USER_ARTICLE: '/recommend/article',
}

export function getArticleRecommend(query: any) {
    return request.post(Api.RECOMMEND_USER_ARTICLE, query)
}
