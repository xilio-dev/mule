import {request} from '@/utils/request/Axios.ts';

const Api = {
    TAG_LIST: '/tag/list',

};

export function tagList() {
    return request.get(Api.TAG_LIST)
}
