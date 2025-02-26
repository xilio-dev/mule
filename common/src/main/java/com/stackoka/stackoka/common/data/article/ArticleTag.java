package com.stackoka.stackoka.common.data.article;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2025-02-24
 */
@Getter
@Setter
@ToString
@TableName("article_tag")
public class ArticleTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("article_id")
    private String articleId;

    @TableField("tag_id")
    private String tagId;
}
