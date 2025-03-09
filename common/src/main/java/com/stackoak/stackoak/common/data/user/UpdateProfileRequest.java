package com.stackoak.stackoak.common.data.user;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UpdateProfileRequest implements Serializable {
    /**
     * 用户名
     */
    private String nickname;

    /**
     * 公司
     */
    private String company;

    /**
     * 职位
     */
    private String jobTitle;

    /**
     * 大头像链接
     */
    private String avatar;


    /**
     * 描述
     */
    private String introduce;

    /**
     * 个人博客地址
     */
    private String personBlogAddress;


    /**
     * 大学名称
     */
    private String universityName;

    /**
     * 大学标志
     */
    private String universityLogo;

    /**
     * 专业名称
     */
    private String majorName;


    /**
     * Github主页
     */
    private String github;

    /**
     * Gitee主页
     */
    private String gitee;

    /**
     * 性别：0女、1男、2保密
     */
    private Integer gender;

    /**
     * csdn主页
     */
    private String csdn;

    /**
     * 博客园主页
     */
    private String bokeyuan;

    /**
     * 哔哩哔哩主页
     */
    private String bilibli;

    /**
     * 学历背景
     */
    private Integer eduLevel;

    /**
     * 入学时间
     */
    private LocalDateTime eduStartTime;

    /**
     * 毕业时间
     */
    private LocalDateTime eduEndTime;

    /**
     * 开始工作时间
     */
    private LocalDateTime jobTime;

    /**
     * 用户二维码
     */
    private String authorQr;

    /**
     * 职业领域
     */
    private Integer careerField;
}
