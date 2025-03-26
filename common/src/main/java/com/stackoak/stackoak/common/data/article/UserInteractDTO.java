package com.stackoak.stackoak.common.data.article;

import lombok.Data;

import java.io.Serializable;
@Data
public  class UserInteractDTO implements Serializable {
    private String userId;
    private Boolean isLike=false;
    private Boolean isFollow=false;
    private Boolean isCollect=false;

}
