package cn.xilio.project.bo;

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
 * @author xilio.cn
 * @since 2025-02-24
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
    private Long columnId;

    /**
     * 文章编号
     */
    @TableId("article_id")
    private Long articleId;
}
