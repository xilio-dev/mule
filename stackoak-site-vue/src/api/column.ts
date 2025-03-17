import {request} from '@/utils/request/Axios.ts';

const Api = {
    COLUMN_LIST: '/column/list',
    COLUMN_LISTS: '/column/lists',
    LIST_BY_USER: '/column/list_by_user',
    SAVE_COLUMN: '/column/save',
    DELETE_COLUMN: '/column/del',

};

export function columnList(pageQuery: any) {
    return request.post(Api.COLUMN_LIST, pageQuery)
}

export function columnLists(pageQuery: any) {
    return request.post(Api.COLUMN_LISTS, pageQuery)
}

export function columnListByUser(query: any) {
    return request.post(Api.LIST_BY_USER, query)
}

export function saveColumn(data: any) {
    return request.post(Api.SAVE_COLUMN, data)
}

export function deleteColumn(columnId: string) {
    return request.delete(Api.DELETE_COLUMN, {
        params: {
            columnId: columnId
        }
    })
}
