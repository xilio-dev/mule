package com.stackoak.stackoak.common.data.column;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * <p>
 *
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-23
 */
@Getter
@Setter
@ToString
@TableName("columns")
@NoArgsConstructor
public class Column implements Serializable {

    private static final long serialVersionUID = 1L;

    public Column(String name, String userId) {
        this.name = name;
        this.userId = userId;
    }

    @TableId(value = "id", type= IdType.ASSIGN_UUID)
    private String id;

    /**
     * 专栏名称
     */
    @TableField("name")
    private String name;

    /**
     * 封面
     */
    @TableField("cover")
    private String cover;

    /**
     * 创建者
     */
    @TableField("user_id")
    private String userId;

    /**
     * 简介
     */
    @TableField("intro")
    private String intro;
}
