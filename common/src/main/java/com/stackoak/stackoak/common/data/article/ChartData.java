package com.stackoak.stackoak.common.data.article;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ChartData implements Serializable {
    private List<Integer> view;
    private List<Integer> collect;
    private List<Integer> comment;
    private List<Integer> like;
}
