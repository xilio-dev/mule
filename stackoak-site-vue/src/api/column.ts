import {request} from '@/utils/request/Axios.ts';

const Api = {
    COLUMN_LIST: '/column/list',

};
export function columnList() {
    return request.get(Api.COLUMN_LIST)
}
