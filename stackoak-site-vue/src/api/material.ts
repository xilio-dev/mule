import {request} from '@/utils/request/Axios.ts';

const Api = {
    LIST: '/material/list',
    BIND: '/material/bind',

};
export function materialList() {
    return request.get(Api.LIST)
}
export function bindMaterial(materialId:object) {
    return request.put(Api.BIND,materialId)
}
