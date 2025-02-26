package com.stackoka.stackoka.common.data.opensource;

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
@TableName("opensource")
public class Opensource implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;
}
