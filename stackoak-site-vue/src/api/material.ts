import {request} from '@/utils/request/Axios.ts';

const Api = {
    LIST: '/material/list',
    BIND: '/material/bind',
    UPLOAD_IMAGE: '/material/image/upload',

};
export function materialList() {
    return request.get(Api.LIST)
}
export function bindMaterial(materialId:any) {
    return request.put(Api.BIND,materialId)
}
export function uploadImage(file:any) {
    return request.post(Api.UPLOAD_IMAGE,file)
}


