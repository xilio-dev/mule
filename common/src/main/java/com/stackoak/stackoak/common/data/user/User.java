package com.stackoak.stackoak.common.data.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-28 19:46:48
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
     * 权力值
     */
    @TableField("power")
    private Integer power;

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
     * 管理员
     */
    @TableField("administrator")
    private Integer administrator;

    /**
     * 微信验证
     */
    @TableField("wechat_verified")
    private Integer wechatVerified;

    /**
     * 微博验证
     */
    @TableField("gitee_verified")
    private Integer giteeVerified;

    /**
     * GitHub验证
     */
    @TableField("github_verified")
    private Integer githubVerified;

    /**
     * 关注人数
     */
    @TableField("followee_count")
    private Integer followeeCount;

    /**
     * 粉丝数
     */
    @TableField("follower_count")
    private Integer followerCount;

    /**
     * 发布文章数
     */
    @TableField("post_article_count")
    private Integer postArticleCount;

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
     * 订阅标签数
     */
    @TableField("subscribe_tag_count")
    private Integer subscribeTagCount;

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
     * 发布短消息数
     */
    @TableField("post_shortmsg_count")
    private Integer postShortmsgCount;

    /**
     * 评论短消息数
     */
    @TableField("comment_shortmsg_count")
    private Integer commentShortmsgCount;

    /**
     * 点赞短消息数
     */
    @TableField("like_shortmsg_count")
    private Integer likeShortmsgCount;

    /**
     * 是否被关注
     */
    @TableField("isfollowed")
    private Integer isfollowed;

    /**
     * 禁用词
     */
    @TableField("forbidden_words")
    private Integer forbiddenWords;

    /**
     * 创建收藏集数
     */
    @TableField("create_collect_set_count")
    private Integer createCollectSetCount;

    /**
     * 关注收藏集数
     */
    @TableField("follow_collect_set_count")
    private Integer followCollectSetCount;

    /**
     * 微博昵称
     */
    @TableField("gitee_nickname")
    private String giteeNickname;

    /**
     * 微信昵称
     */
    @TableField("wechat_nickname")
    private String wechatNickname;

    /**
     * GitHub昵称
     */
    @TableField("github_nickname")
    private String githubNickname;

    /**
     * 申请注销
     */
    @TableField("apply_logout")
    private Integer applyLogout;

    /**
     * 是否注销
     */
    @TableField("is_logout")
    private Integer isLogout;

    /**
     * 是否新用户
     */
    @TableField("is_new")
    private Integer isNew;

    /**
     * 学习积分
     */
    @TableField("study_point")
    private Integer studyPoint;

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
     * 学生状态
     */
    @TableField("student_status")
    private Integer studentStatus;

//    /**
//     * 毕业时间
//     */
//    @TableField("graduated_at")
//    private LocalDateTime graduatedAt;

    /**
     * 关注专栏数
     */
    @TableField("follow_column_cnt")
    private Integer followColumnCnt;

    /**
     * 是否需要引导
     */
    @TableField("need_lead")
    private Integer needLead;

    /**
     * 是否VIP
     */
    @TableField("is_vip")
    private Integer isVip;

    /**
     * 收藏集文章数
     */
    @TableField("collection_set_article_count")
    private Integer collectionSetArticleCount;

    /**
     * 用户不喜欢状态
     */
    @TableField("user_dislike_status")
    private Integer userDislikeStatus;
}
