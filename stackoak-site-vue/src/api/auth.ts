
import {request} from '@/utils/request/Axios.ts';

const Api = {
    emailLogin: '/auth/emailLogin',
    emailCodeLogin: '/auth/emailCodeLogin',
    LOGOUT: '/auth/logout',
    CHANGE_EMAIL_BIND: '/auth/change-email-bind',
};
export function emailLogin(emailLoginDTO: any) {
    return request.post(Api.emailLogin, emailLoginDTO)
}
// 导出一个函数，用于邮箱验证码登录
export function emailCodeLogin(emailCodeLogin: any) {
    // 发送post请求，请求地址为Api.emailCodeLogin，请求参数为emailCodeLogin
    return request.post(Api.emailCodeLogin, emailCodeLogin)
}
export function sendLogout() {
    return request.post(Api.LOGOUT)
}
// 导出一个函数，用于修改邮箱绑定
export function changeEmailBind(data: any) {
    // 发送put请求，修改邮箱绑定
    return request.put(Api.CHANGE_EMAIL_BIND, data)
}


