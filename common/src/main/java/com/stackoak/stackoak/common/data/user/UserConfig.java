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
 *
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-12 03:37:03
 */
@Getter
@Setter
@ToString
@TableName("user_config")
public class UserConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id",type = IdType.ASSIGN_UUID)
    private String userId;

    /**
     * 代码主题
     */
    @TableField("code_theme")
    private Integer codeTheme;

    /**
     * 编辑器主题
     */
    @TableField("main_theme")
    private Integer mainTheme;

    /**
     * 脚标风格
     */
    @TableField("anchor_style")
    private Integer anchorStyle;

    /**
     * 编辑器是否开启悬浮工具栏
     */
    @TableField("open_float_tool")
    private Boolean openFloatTool;
}
