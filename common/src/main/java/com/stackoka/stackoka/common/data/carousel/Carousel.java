package com.stackoka.stackoka.common.data.carousel;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("carousel")
public class Carousel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type= IdType.ASSIGN_ID)
    private String id;
}
