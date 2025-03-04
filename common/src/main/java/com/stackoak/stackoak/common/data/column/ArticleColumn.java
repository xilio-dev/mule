package com.stackoak.stackoak.common.data.column;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * <p>
 * 文章专栏
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-26 23:05:19
 */
@Getter
@Setter
@ToString
@TableName("article_column")
@NoArgsConstructor
public class ArticleColumn implements Serializable {

    private static final long serialVersionUID = 1L;

    public ArticleColumn(String columnId, String articleId) {
        this.columnId = columnId;
        this.articleId = articleId;
    }

    /**
     * 专栏编号
     */
    private String columnId;

    /**
     * 文章编号
     */
    private String articleId;
}
