package com.stackoka.stackoka.common.data.friendlink;

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
 * @since 2025-02-28 15:46:24
 */
@Getter
@Setter
@ToString
@TableName("friendlink")
public class FriendLink implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    @TableField("name")
    private String name;

    @TableField("logo")
    private String logo;

    @TableField("type")
    private Integer type;

    @TableField("link")
    private String link;

    @TableField("status")
    private Integer status;
}
