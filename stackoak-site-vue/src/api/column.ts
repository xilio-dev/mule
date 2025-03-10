import {request} from '@/utils/request/Axios.ts';

const Api = {
    COLUMN_LIST: '/column/list',

};
export function columnList(pageQuery:any) {
    return request.post(Api.COLUMN_LIST,pageQuery)
}
