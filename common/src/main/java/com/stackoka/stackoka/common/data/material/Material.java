package com.stackoka.stackoka.common.data.material;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * <p>
 *
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-24 17:49:31
 */
@Getter
@Setter
@ToString
@TableName("material")
public class Material implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    @TableField("user_id")
    private String userId;

    @TableField("title")
    private String title;

    @TableField("img_url")
    private String imgUrl;

    @TableField("small_img")
    private String smallImg;

    @TableField("remark")
    private String remark;

    @TableField("width")
    private Integer width;

    @TableField("keyword")
    private String keyword;

    @TableField("height")
    private Integer height;

    @TableField("uploaded_at")
    private LocalDateTime uploadedAt;

    /**
     * 0:系统素材；1:用户上传
     */
    @TableField("type")
    private Integer type;

    @TableField("is_deleted")
    private Integer deleted;

    @TableField("status")
    private Integer status;

    @TableField("spice")
    private Integer spice;
}
