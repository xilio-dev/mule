package com.stackoak.stackoak.common.data.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-05 16:20:53
 */
@Getter
@Setter
@ToString
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 公司
     */
    @TableField("company")
    private String company;

    /**
     * 职位
     */
    @TableField("job_title")
    private String jobTitle;

    /**
     * 大头像链接
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 等级
     */
    @TableField("level")
    private Integer level;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 是否允许通知
     */
    @TableField("allow_notification")
    private Integer allowNotification;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 博客地址
     */
    @TableField("blog_address")
    private String blogAddress;

    /**
     * 是否黑名单
     */
    @TableField("is_black")
    private Integer isBlack;

    /**
     * 注册时间
     */
    @TableField("register_time")
    private Long registerTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Long updateTime;

    /**
     * 关注人数
     */
    @TableField("follow_count")
    private Integer followCount;

    /**
     * 粉丝数
     */
    @TableField("follower_count")
    private Integer followerCount;

    /**
     * 发布文章数
     */
    @TableField("article_count")
    private Integer articleCount;

    /**
     * 点赞文章数
     */
    @TableField("like_article_count")
    private Integer likeArticleCount;

    /**
     * 查看文章数
     */
    @TableField("view_article_count")
    private Integer viewArticleCount;

    /**
     * 获得点赞数
     */
    @TableField("got_like_count")
    private Integer gotLikeCount;

    /**
     * 获得查看数
     */
    @TableField("got_view_count")
    private Integer gotViewCount;

    /**
     * 评论数
     */
    @TableField("comment_count")
    private Integer commentCount;

    /**
     * 收藏集数
     */
    @TableField("collect_set_count")
    private Integer collectSetCount;

    /**
     * GitHub昵称
     */
    @TableField("github_nickname")
    private String githubNickname;

    /**
     * 大学名称
     */
    @TableField("university_name")
    private String universityName;

    /**
     * 大学标志
     */
    @TableField("university_logo")
    private String universityLogo;

    /**
     * 专业名称
     */
    @TableField("major_name")
    private String majorName;

    /**
     * 是否需要引导
     */
    @TableField("need_lead")
    private Integer needLead;

    /**
     * 用户状态：1正常；2封禁
     */
    @TableField("status")
    private Integer status;

    /**
     * 用户密码
     */
    @TableField("password")
    private String password;
}
