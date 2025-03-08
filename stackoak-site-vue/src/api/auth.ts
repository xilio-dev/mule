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
export function emailCodeLogin(emailCodeLogin: any) {
    return request.post(Api.emailCodeLogin, emailCodeLogin)
}
export function sendLogout() {
    return request.post(Api.LOGOUT)
}
export function changeEmailBind(data: any) {
    return request.put(Api.CHANGE_EMAIL_BIND, data)
}

