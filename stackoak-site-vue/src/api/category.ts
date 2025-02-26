import {request} from '@/utils/request/Axios.ts';

const Api = {
    categoryList: '/category/list',
};
export function categoryList() {
    return request.get(Api.categoryList)
}


