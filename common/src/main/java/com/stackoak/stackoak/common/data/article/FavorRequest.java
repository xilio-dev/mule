package com.stackoak.stackoak.common.data.article;

import java.io.Serializable;

/**
 * 点赞请求
 *
 * @param aid       文章编号
 * @param collectId 收藏编号
 */
public record FavorRequest(String aid, String collectId) implements Serializable {
}
