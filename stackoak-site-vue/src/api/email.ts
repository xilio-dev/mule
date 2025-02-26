import {request} from '@/utils/request/Axios.ts';

const Api = {
    sendEmail: '/email/send',
};

export function sendEmail(email: string) {
    return request.post(Api.sendEmail, {email: email})
}
