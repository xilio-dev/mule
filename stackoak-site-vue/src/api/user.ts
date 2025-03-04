import {request} from '@/utils/request/Axios.ts';

const Api = {
    getUserInfo: '/user/get',
    FOLLOWS: '/user/follows',
    FANS: '/user/fans',
};

export function getUserInfo() {
    return request.get(Api.getUserInfo)
}

export function getFollows(pageQuery: any) {
    return request.post(Api.FOLLOWS, pageQuery)
}

export function getFans(pageQuery: any) {
    return request.post(Api.FANS, pageQuery)
}
