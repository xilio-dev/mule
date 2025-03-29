import {defineStore} from "pinia";
import {ref} from "vue";
interface ThemeInfo {
    author_background?: string;/*临时上下文*/
}

export const useThemeStore = defineStore('theme', () => {
    const themeInfo = ref<ThemeInfo>({
        author_background: '',
    });

    // 定义 setUserInfo 方法，参数类型为 UserInfo
    function setAuthorBackground(bg: string) {
        themeInfo.value = {author_background: bg};
    }

    function getAuthorBackground() {
        return themeInfo.value.author_background
    }
    return {getAuthorBackground, setAuthorBackground,themeInfo};
});
