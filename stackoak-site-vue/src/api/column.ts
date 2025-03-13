import {request} from '@/utils/request/Axios.ts';

const Api = {
    COLUMN_LIST: '/column/list',
    COLUMN_LISTS: '/column/lists',

};
export function columnList(pageQuery:any) {
    return request.post(Api.COLUMN_LIST,pageQuery)
}
export function columnLists(pageQuery:any) {
    return request.post(Api.COLUMN_LISTS,pageQuery)
}
