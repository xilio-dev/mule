package cn.xilio.project.bo;

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
 * 文章表
 * </p>
 *
 * @author xilio.cn
 * @since 2025-02-15
 */
@Getter
@Setter
@ToString
@TableName("article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId("id")
    private Long id;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 封面
     */
    @TableField("cover")
    private String cover;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 被点赞数量
     */
    @TableField("like_count")
    private Integer likeCount;

    /**
     * 被浏览数量
     */
    @TableField("view_count")
    private Integer viewCount;

    /**
     * 被评论数量
     */
    @TableField("comment_count")
    private Integer commentCount;

    /**
     * 发布时间
     */
    @TableField("publish_time")
    private LocalDateTime publishTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 区域
     */
    @TableField("zone")
    private Byte zone;

    /**
     * 被收藏数量
     */
    @TableField("collect_count")
    private Integer collectCount;

    /**
     * 内容字数
     */
    @TableField("content_count")
    private Integer contentCount;

    /**
     * 作者ID
     */
    @TableField("member_id")
    private Long memberId;
}
