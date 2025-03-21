package com.stackoak.stackoak.common.data.column;

import com.stackoak.stackoak.common.data.article.UserInteractDTO;
import com.stackoak.stackoak.common.data.user.User;
import lombok.Data;

@Data
public class ColumnDetailVo {
    // 文章所属的列
    private Column column;
    // 文章的作者
    private User userInfo;
    // 当前访问者是否关注了该专栏
    private Boolean isFollowColumn;
    // 当前访问者是否关注了作者
    private Boolean isFollowAuthor;
    // 文章数量
    private Integer articleTotalCount;
    // 订阅人数
    private Integer subTotalCount;
    // 浏览数量
    private Integer viewTotalCount;
}
