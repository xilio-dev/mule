import {request} from '@/utils/request/Axios.ts';

const Api = {
    NOTIFY_SETTING_SET: '/notify-setting/set',
    GET_SETTING: '/notify-setting/get-setting',

};

export function setNotifySetting(data: any) {
    return request.put(Api.NOTIFY_SETTING_SET, data)
}

export function getNotifySetting() {
    return request.get(Api.GET_SETTING)
}



