package com.stackoak.stackoak.common.data.material;

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
 * @since 2025-03-26 01:28:37
 */
@Getter
@Setter
@ToString
@TableName("theme_photo")
public class ThemePhoto implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 小图
     */
    @TableField("s_img")
    private String sImg;

    /**
     * 大图
     */
    @TableField("l_img")
    private String lImg;

    /**
     * 类型：1-用户背景图、2-用户顶部封面图
     */
    @TableField("type")
    private Integer type;

    /**
     * 状态：1-正常、0-禁用
     */
    @TableField("status")
    private Integer status;

    /**
     * 分类：图片的类型，用于对图片进行分类展示，方便筛选
     */
    @TableField("cid")
    private String cid;
}
