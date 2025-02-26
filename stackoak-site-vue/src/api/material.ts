import {request} from '@/utils/request/Axios.ts';

const Api = {
    LIST: '/material/list',
    BIND: '/material/bind',

};
export function materialList() {
    return request.get(Api.LIST)
}
export function bindMaterial(id:string) {
    return request.post(Api.BIND+`/${id}`)
}
