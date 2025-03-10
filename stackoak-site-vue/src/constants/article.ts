//文章模块相关常量
export const ARTICLE = {
    // 可见范围枚举
    VisibilityStatusEnum: {
        ALL: 1,
        PRIVATE: 2,
        FOLLOWERS: 3,
        PASSWORD: 4
    } as const,
    // 创作类型枚举
    CreativeTypeEnum: {
        ORIGINAL: 1,
        REPRINT: 2
    } as const,

    // 可见范围列表
    get VISIBLE_STATUS_LIST() {
        return [
            { id: this.VisibilityStatusEnum.ALL, label: '全部' },
            { id: this.VisibilityStatusEnum.PRIVATE, label: '仅自己' },
            { id: this.VisibilityStatusEnum.FOLLOWERS, label: '粉丝' },
            { id: this.VisibilityStatusEnum.PASSWORD, label: '密码访问' }
        ];
    },

    // 创作类型列表
    get CREATIVE_TYPE_LIST() {
        return [
            { id: this.CreativeTypeEnum.ORIGINAL, label: '原创' },
            { id: this.CreativeTypeEnum.REPRINT, label: '转载' }
        ];
    }
};
