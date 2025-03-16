import {request} from '@/utils/request/Axios.ts';

const Api = {
    categoryList: '/category/list',
    twoLevelCategoryTree: '/category/cate',
};
export function categoryList() {
    return request.get(Api.categoryList)
}
export function twoLevelCategoryTree() {
    return request.get(Api.twoLevelCategoryTree)
}



