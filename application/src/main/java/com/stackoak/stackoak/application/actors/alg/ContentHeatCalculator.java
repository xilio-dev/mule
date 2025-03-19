package com.stackoak.stackoak.application.actors.alg;

import org.springframework.stereotype.Component;

/**
 * 文章内容热度计算服务
 */
@Component
public class ContentHeatCalculator {
    /**
     * 衰减的重力参数
     */
    private static final double G = 1.5;
    /**
     * 点赞
     */
    private Integer likeWeight = 4;
    /**
     * 阅读
     */
    private Integer viewWeight = 3;
    /**
     * 收藏
     */
    private Integer collectWeight = 5;
    /**
     * 评论
     */
    private Integer commentWeight = 4;

    /**
     * 计算内容热度
     *
     * @param W 文章阅读数、评论数、点赞数加权求和的数值 hot_index
     * @param I 作者的影响因子
     * @param T 文章自发布以来的时长
     * @return 热度值
     */
    private int calculateHeat(double W, double I, double T) {
        // 计算热度
        double heat = (W + I) / Math.pow(T + 1, G);
        // 将结果四舍五入转换为 int 类型
        return (int) Math.round(heat);
    }

    /**
     * 计算文章热度
     *
     * @param W         文章阅读数、评论数、点赞数加权求和的数值 hot_index
     * @param influence 作者的影响因子
     * @param T         文章自发布以来的时长 （天）
     * @return 热度
     */
    public int calculateHotIndex(double influence, double T, ArticleAnalyse W) {
        int w = (W.getLikeCount() * likeWeight) + (W.getViewCount() * viewWeight) + (W.getCollectCount() * collectWeight) + (W.getCommentCount() * commentWeight);
        return calculateHeat(w, influence, T);
    }

    public static void main(String[] args) {
        ContentHeatCalculator contentHeatCalculator = new ContentHeatCalculator();
        int hot = contentHeatCalculator.calculateHotIndex(1000, 1000, new ArticleAnalyse(1000, 7856, 60053, 5256));
        System.out.println(hot);
    }
}
