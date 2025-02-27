package com.stackoka.stackoka.common.data.collect;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
 * @since 2025-02-27 23:00:30
 */
@Getter
@Setter
@ToString
@TableName("article_collect")
@NoArgsConstructor
public class ArticleCollect implements Serializable {

    private static final long serialVersionUID = 1L;

    public ArticleCollect(String articleId, String collectId) {
        this.articleId = articleId;
        this.collectId = collectId;
    }

    @TableField("article_id")
    private String articleId;

    @TableField("collect_id")
    private String collectId;
}
