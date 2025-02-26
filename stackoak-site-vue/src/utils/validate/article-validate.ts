import { message } from 'ant-design-vue';

export const validateFieldAndLength = (value: string | undefined, minLength: number, fieldName: string) => {
    if (value === undefined) {
        message.warning(`${fieldName}不能为空！`);
        return false;
    }
    if (value.length < minLength) {
        message.warning(`${fieldName}至少${minLength}个字`);
        return false;
    }
    return true;
};
