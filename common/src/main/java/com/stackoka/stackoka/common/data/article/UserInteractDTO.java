package com.stackoka.stackoka.common.data.article;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserInteractDTO implements Serializable {
    private Integer omitEmpty=1;
    private String userId="0";
    private Boolean isDigg=true;
    private Boolean isFollow=true;
    private Boolean isCollect=true;
}
