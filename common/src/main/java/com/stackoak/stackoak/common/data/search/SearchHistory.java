package com.stackoak.stackoak.common.data.search;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

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
 * @since 2025-03-04 12:31:46
 */
@Getter
@Setter
@ToString
@TableName("search_history")
@NoArgsConstructor
public class SearchHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    public SearchHistory(String userId, String keyword) {
        this.userId = userId;
        this.keyword = keyword;
    }

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 用户编号
     */
    @TableField("user_id")
    private String userId;

    /**
     * 关键字
     */
    @TableField("keyword")
    private String keyword;
    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    /**
     * 逻辑删除
     */
    @TableField("deleted")
    @TableLogic
    private String deleted;


}
