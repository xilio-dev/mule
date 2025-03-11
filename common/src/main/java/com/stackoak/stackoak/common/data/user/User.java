package com.stackoak.stackoak.common.data.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-09 17:50:08
 */
@Getter
@Setter
@ToString
@TableName("user")
@JsonFilter("userFilter")
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
    @TableField("nickname")
    private String nickname;

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
     * 描述
     */
    @TableField("introduce")
    private String introduce;

    /**
     * 个人博客地址
     */
    @TableField("person_blog_address")
    private String personBlogAddress;

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
    @TableField("fans_count")
    private Integer fansCount;

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

    /**
     * Github主页
     */
    @TableField("github")
    private String github;

    /**
     * Gitee主页
     */
    @TableField("gitee")
    private String gitee;

    /**
     * 性别：0女、1男、2保密
     */
    @TableField("gender")
    private Integer gender;

    /**
     * csdn主页
     */
    @TableField("csdn")
    private String csdn;

    /**
     * 博客园主页
     */
    @TableField("bokeyuan")
    private String bokeyuan;

    /**
     * 哔哩哔哩主页
     */
    @TableField("bilibli")
    private String bilibli;

    /**
     * 学历背景
     */
    @TableField("edu_level")
    private Integer eduLevel;

    /**
     * 入学时间
     */
    @TableField("edu_start_time")
    private LocalDate eduStartTime;

    /**
     * 毕业时间
     */
    @TableField("edu_end_time")
    private LocalDate eduEndTime;

    /**
     * 开始工作时间
     */
    @TableField("job_time")
    private LocalDate jobTime;

    /**
     * 用户二维码
     */
    @TableField("author_qr")
    private String authorQr;

    /**
     * 职业领域
     */
    @TableField("career_field")
    private Integer careerField;

    /**
     * 兴趣标签
     */
    @TableField("tag_ids")
    private String tagIds;
}
