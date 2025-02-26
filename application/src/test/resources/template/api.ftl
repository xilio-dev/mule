import {request} from '@/utils/request/Axios.ts';

const Api = {
<#list apiNames as apiName>
    ${apiName}: '/${moduleName}/${apiName}',
</#list>
};
<#list apiNames as apiName>
export function ${apiName}(query: any) {
    return request.post(Api.${apiName}, query)
}
</#list>

