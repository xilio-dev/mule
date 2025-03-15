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
    /**
     * 从github或者gitee仓库链接中解析出用户名和项目名字
     * @param url
     */
    static parseGitUrl = (url: string): { username: string, projectName: string } => {
        // 处理 git@ 开头的 URL
        if (url.startsWith('git@')) {
            const parts = url.split(/[:/]/);
            if (parts.length < 3) {
                throw new Error('Invalid Git URL. Expected format: git@host:username/repo.git');
            }
            const username = parts[parts.length - 2];
            const projectName = parts[parts.length - 1].replace('.git', '');
            return { username, projectName };
        }

        // 处理 https:// 或 http:// 开头的 URL
        if (url.startsWith('https://') || url.startsWith('http://')) {
            const urlObj = new URL(url);
            const pathParts = urlObj.pathname.split('/').filter(part => part !== '');
            if (pathParts.length < 2) {
                throw new Error('Invalid Git URL. Expected format: https://host/username/repo.git');
            }
            const username = pathParts[0];
            const projectName = pathParts[1].replace('.git', '');
            return { username, projectName };
        }

        throw new Error('Unsupported URL format');
    };
}
