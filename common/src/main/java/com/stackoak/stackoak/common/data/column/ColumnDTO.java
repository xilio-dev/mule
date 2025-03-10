package com.stackoak.stackoak.common.data.column;


import lombok.Data;

import java.io.Serializable;
@Data
public class ColumnDTO implements Serializable {
    private String id;

    /**
     * 专栏名称
     */
    private String name;
}
