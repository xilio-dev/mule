package com.stackoak.stackoak.common.data.siteconfig;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * <p>
 *
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-04 09:15:27
 */
@Getter
@Setter
@ToString
@TableName("site_config")
public class SiteConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 网站logo
     */
    @TableField("logo_url")
    private String logoUrl;

    /**
     * 举报邮箱
     */
    @TableField("report_mail")
    private String reportMail;

    /**
     * 用户协议
     */
    @TableField("user_agreement")
    private String userAgreement;

    /**
     * 公安备案号
     */
    @TableField("security_record")
    private String securityRecord;

    /**
     * 域名
     */
    @TableField("domain_name")
    private String domainName;

    /**
     * 公司名字
     */
    @TableField("company_name")
    private String companyName;

    /**
     * 隐私政策
     */
    @TableField("privacy")
    private String privacy;

    /**
     * 版权
     */
    @TableField("copyright")
    private String copyright;

    /**
     * 是否开启网站用户注册
     */
    @TableField("open_registration")
    private Integer openRegistration;

    /**
     * 是否开启网站用户登陆
     */
    @TableField("open_login")
    private Integer openLogin;

    /**
     * 是否开启PC站点，应急及时关闭站点展示
     */
    @TableField("open_pc_site")
    private Integer openPcSite;

    @TableId("id")
    private String id;
}
