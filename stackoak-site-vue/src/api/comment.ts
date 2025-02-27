import {request} from '@/utils/request/Axios.ts';

const Api = {
    DIGG: '/comment/digg',
    UN_DIGG: '/comment/undigg',
};

export function diggComment(data: any) {
    return request.post(Api.DIGG, data)
}

export function unDiggComment(data: any) {
    return request.put(Api.UN_DIGG, data)
}
