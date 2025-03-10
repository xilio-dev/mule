import {request} from '@/utils/request/Axios.ts';

const Api = {
    TAG_LIST: '/tag/recommend-tags',

};

export function tagList(pageQuery:any) {
    return request.post(Api.TAG_LIST,pageQuery)
}
