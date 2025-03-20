
import {request} from '@/utils/request/Axios.ts';

const Api = {
    emailLogin: '/auth/emailLogin',
    emailCodeLogin: '/auth/emailCodeLogin',
    LOGOUT: '/auth/logout',
    CHANGE_EMAIL_BIND: '/auth/change-email-bind',
    GET_QR_CODE: '/scan/get_qr_code',
    CHECK_STATUS: '/scan/check_status',
    QR_LOGIN: '/scan/qr_login',
};
export function emailLogin(emailLoginDTO: any) {
    return request.post(Api.emailLogin, emailLoginDTO)
}
// 导出一个函数，用于邮箱验证码登录
export function emailCodeLogin(emailCodeLogin: any) {

    return request.post(Api.emailCodeLogin, emailCodeLogin)
}
export function sendLogout() {
    return request.post(Api.LOGOUT)
}
// 导出一个函数，用于修改邮箱绑定
export function changeEmailBind(data: any) {

    return request.put(Api.CHANGE_EMAIL_BIND, data)
}
export function getQrCode() {

    return request.get(Api.GET_QR_CODE)
}
export function checkScanStatus(param:any) {

    return request.post(Api.CHECK_STATUS,param)
}
export function qrLogin(param:any) {

    return request.post(Api.QR_LOGIN,param)
}





