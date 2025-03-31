package com.stackoak.stackoak.common.data.article;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 单篇文章数据统计表
 */
@Data
public class SingleArticleStatisticsVo implements Serializable {
    private List<String> dateList;
    private ChartData chartData;
    private List<ArticleData>list;
    private Long page;
    private Long size;
    private Long total;
}
