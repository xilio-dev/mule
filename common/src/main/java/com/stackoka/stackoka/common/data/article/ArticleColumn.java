package com.stackoka.stackoka.common.data.article;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * <p>
 * 文章专栏
 * </p>
 *
 * @author stackoka.com
 * @since 2025-02-23
 */
@Getter
@Setter
@ToString
@TableName("article_column")
public class ArticleColumn implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 专栏编号
     */
    @TableId("column_id")
    private String columnId;

    /**
     * 文章编号
     */
    @TableId("article_id")
    private Long articleId;
}
