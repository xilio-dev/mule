package com.stackoak.stackoak.common.data.group;

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
 * @since 2025-02-15
 */
@Getter
@Setter
@ToString
@TableName("group")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;
}
