export class CommonUtil {
    /**
     * 打开一个新的页面
     * @param url 路由地址
     */
    static openNewPage = (url: string) => {
        window.open(url, '_blank')
    }
    /**
     * 在当前页打开
     * @param url 路由
     */
    static openPage = (url: string) => {
        window.open(url)
    }
}
