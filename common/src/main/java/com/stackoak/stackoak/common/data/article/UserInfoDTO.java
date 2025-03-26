package com.stackoak.stackoak.common.data.article;

import lombok.Data;

@Data
// 用户信息数据传输对象
public class UserInfoDTO {
    // 用户ID
    private String userId;
    // 昵称
    private String nickname;
    // 头像
    private String avatar;
    // 职位
    private String jobTitle;
    // 文章数量
    private Integer articleCount;
    // 收到的点赞数量
    private Integer gotLikeCount;
    // 粉丝数量
    private Integer fansCount;
    // 收到的收藏数量
    private Integer gotCollectCount;
    /**
     * 背景图
     */
    private String bgPhoto;

    /**
     * 作者中心顶部背景图
     */
    private String topPhoto;

    /**
     * 用户编辑器代码主题样式
     */
    private String editorCodeTheme;

    /**
     * 编辑器主题
     */
    private String editorMainTheme;

    /**
     * 编辑器预览时目录样式
     */
    private String editorAnchorStyle;

    /**
     * 编辑器是否开启悬浮快捷工具
     */
    private Boolean editorFloatToolEnable;
}
