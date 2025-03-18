import {request} from '@/utils/request/Axios.ts';

const Api = {
    NOTIFY_SETTING_SET: '/notify-setting/set',
    GET_SETTING: '/notify-setting/get-setting',
    UNREAD_COUNT: '/notification/unread_count',
    SET_ALL_READ: '/notification/set_all_read',
    GET_MESSAGE: '/notification/get_message',

};

export function setNotifySetting(data: any) {
    return request.put(Api.NOTIFY_SETTING_SET, data)
}

export function getNotifySetting() {
    return request.get(Api.GET_SETTING)
}
export function getUnreadCount() {
    return request.get(Api.UNREAD_COUNT)
}
export function setAllRead(body:any) {
    return request.post(Api.SET_ALL_READ,body)
}
export function getMessage(body:any) {
    return request.post(Api.GET_MESSAGE,body)
}




