import {request} from '@/utils/request/Axios.ts';

const Api = {
    LIST: '/friendlink/list',
};

export function friendLinkList() {
    return request.get(Api.LIST)
}
