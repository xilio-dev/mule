import {request} from '@/utils/request/Axios.ts';

const Api = {
    sendEmail: '/email/send',
    VALID_EMAIL_CODE: '/email/valid-email',
};

export function sendEmail(email: string) {
    return request.post(Api.sendEmail, {email: email})
}

export function validEmailCode(email: any) {
    return request.post(Api.VALID_EMAIL_CODE, email)
}
