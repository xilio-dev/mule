package com.stackoak.stackoak.common.data.collect;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * <p>
 * 收藏夹

 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-27 23:00:30
 */
@Getter
@Setter
@ToString
@TableName("collect")
public class Collect implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("id")
    private String id;

    /**
     * 名字
     */
    @TableField("name")
    private String name;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 创建者
     */
    @TableField("user_id")
    private String userId;

    /**
     * 状态
     */
    @TableField("status")
    private Integer status;
}
