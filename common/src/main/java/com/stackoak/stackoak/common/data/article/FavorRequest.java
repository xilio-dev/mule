package com.stackoak.stackoak.common.data.article;

import lombok.Data;

import java.io.Serializable;

@Data
public class FavorRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 文章编号
     */
    private String aid;
    /**
     * 收藏夹编号
     */
    private String collectId;
}
