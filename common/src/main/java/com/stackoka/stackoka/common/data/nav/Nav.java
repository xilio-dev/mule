package com.stackoka.stackoka.common.data.nav;

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
 *
 * </p>
 *
 * @author stackoka.com
 * @since 2025-02-15
 */
@Getter
@Setter
@ToString
@TableName("nav")
public class Nav implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一编号
     */
    @TableId("id")
    private String id;

    /**
     * 父菜单：0表示根目录
     */
    @TableField("pid")
    private Long pid;

    /**
     * 展示类型：1表示首页导航；2表示创作中心左侧导航
     */
    @TableField("show_type")
    private Boolean showType;

    /**
     * 导航名字
     */
    @TableField("name")
    private String name;

    /**
     * 路径或链接
     */
    @TableField("url")
    private String url;

    /**
     * url类型：1表示组件跳转路径；2表示外链接
     */
    @TableField("url_type")
    private Boolean urlType;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * badge内容
     */
    @TableField("badge_text")
    private String badgeText;

    /**
     * badge扩展样式
     */
    @TableField("badge_style")
    private String badgeStyle;

    /**
     * 是否禁用
     */
    @TableField("disabled")
    private Byte disabled;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 更新人
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * 备注信息
     */
    @TableField("remark")
    private String remark;

    /**
     * 状态：1正常显示；0:不显示
     */
    @TableField("status")
    private Boolean status;
}
