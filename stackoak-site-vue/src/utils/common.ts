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
     * 检查字符串是否包含特殊字符（默认定义的特殊字符）
     * @param str 输入字符串
     * @returns boolean 是否包含特殊字符
     */
    static hasSpecialChars(str: string): boolean {
        // 定义特殊字符的正则表达式
        const specialChars = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/;
        return specialChars.test(str);
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
            return {username, projectName};
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
            return {username, projectName};
        }

        throw new Error('Unsupported URL format');
    };

    static guideRandom(): string {
        const template = "xxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx";
        let gid = "";

        for (const z of template) {
            // 生成 0 到 15 之间的随机数
            const t = Math.floor(Math.random() * 16);

            if (z === 'x') {
                // 当 z 是 'x' 时，使用随机数 t
                gid += t.toString(16);
            } else if (z === 'y') {
                // 当 z 是 'y' 时，生成 8、9、A、B 某个随机数
                gid += ((t & 0x0f) | 0x08).toString(16);
            } else {
                // 不是 'x' 或 'y'，直接添加原字符
                gid += z;
            }
        }

        // 将字符串转换为大写
        return gid.toUpperCase();
    }

// 用于测试的验证函数
    static isValidGuideRandom(input: string): boolean {
        if (!input) {
            return false;
        }

        // 检查基本格式和长度
        const pattern = /^[0-9A-F]{7}-[0-9A-F]{4}-4[0-9A-F]{3}-[89AB][0-9A-F]{3}-[0-9A-F]{12}$/;
        if (!pattern.test(input)) {
            return false;
        }

        // 分割字符串进行详细验证
        const parts = input.split("-");
        if (parts.length !== 5) {
            return false;
        }

        // 检查各部分长度
        const expectedLengths = [7, 4, 4, 4, 12];
        for (let i = 0; i < parts.length; i++) {
            if (parts[i].length !== expectedLengths[i]) {
                return false;
            }
        }

        // 检查第三个部分的第一个字符必须是 '4'
        if (parts[2].charAt(0) !== '4') {
            return false;
        }

        // 检查第四部分的第一个字符必须是 8, 9, A, 或 B
        const yChar = parts[3].charAt(0);
        return !(yChar !== '8' && yChar !== '9' && yChar !== 'A' && yChar !== 'B');
    }


}
