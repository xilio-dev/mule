//文章模块相关常量
export const ARTICLE = {
    StatusEnum: {
        UNDER_REVIEW: 0, // 审核中
        PUBLISHED: 1,    // 已发布
        PRIVATE: 2,      // 仅我可见
        PASSWORD_PROTECTED: 3, // 密码可见
        FANS_ONLY: 4,    // 粉丝可见
        DRAFT: 5,        // 草稿箱
        RECYCLE_BIN: 6 ,  // 回收站
        REJECTED: 7   // 未通过审核


    } as const,
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
