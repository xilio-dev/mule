import {request} from '@/utils/request/Axios.ts';

const Api = {
    emailLogin: '/auth/emailLogin',
    emailCodeLogin: '/auth/emailCodeLogin',
};
export function emailLogin(emailLoginDTO: any) {
    return request.post(Api.emailLogin, emailLoginDTO)
}
export function emailCodeLogin(emailCodeLogin: any) {
    return request.post(Api.emailCodeLogin, emailCodeLogin)
}


