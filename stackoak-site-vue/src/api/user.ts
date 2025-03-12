import {request} from '@/utils/request/Axios.ts';

const Api = {
    getUserInfo: '/user/get',
    GET_USER_DETAIL: '/user/detail',
    FOLLOWS: '/user/follows',
    FANS: '/user/fans',
    UPDATE_PROFILE: '/user/update-profile',
    GET_USER_PROFILE: '/user/get-profile',
};

export function getUserInfo() {
    return request.get(Api.getUserInfo)
}

export function getUserDetailInfo(userId: string) {
    return request.get(Api.GET_USER_DETAIL + `/${userId}`)
}


export function getFollows(pageQuery: any) {
    return request.post(Api.FOLLOWS, pageQuery)
}

export function getFans(pageQuery: any) {
    return request.post(Api.FANS, pageQuery)
}

export function updateProfile(data: any) {
    return request.put(Api.UPDATE_PROFILE, data)
}

export function getUserProfile() {
    return request.get(Api.GET_USER_PROFILE)
}

