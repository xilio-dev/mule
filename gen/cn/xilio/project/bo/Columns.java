package cn.xilio.project.bo;

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
 * @author xilio.cn
 * @since 2025-02-24
 */
@Getter
@Setter
@ToString
@TableName("columns")
public class Columns implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

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
    private Long userId;

    /**
     * 简介
     */
    @TableField("intro")
    private String intro;

    /**
     * 状态：1正常；2审核中
     */
    @TableField("status")
    private Boolean status;
}
