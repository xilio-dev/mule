import {request} from '@/utils/request/Axios.ts';

const Api = {
    getUserInfo: '/user/get',
};
export function getUserInfo() {
    return request.get(Api.getUserInfo)
}
