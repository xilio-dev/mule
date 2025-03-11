import {request} from '@/utils/request/Axios.ts';

const Api = {
    SITE_CONFIG_INFO: '/config/info',
};

export function getSiteConfigInfo() {
    return request.get(Api.SITE_CONFIG_INFO)
}
