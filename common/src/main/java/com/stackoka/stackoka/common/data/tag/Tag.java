package com.stackoka.stackoka.common.data.tag;

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
 * @author stackoka.com
 * @since 2025-02-15
 */
@Getter
@Setter
@ToString
@TableName("tag")
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type= IdType.ASSIGN_UUID)
    private String id;
    @TableField("name")
    private String name;
    /**
     *
     */
    @TableField("user_id")
    private String userId;
}
