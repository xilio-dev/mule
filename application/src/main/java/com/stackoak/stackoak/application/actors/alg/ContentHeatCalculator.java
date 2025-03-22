package com.stackoak.stackoak.application.actors.alg;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ContentHeatCalculator {
    private final double gravity;  // 从配置读取衰减参数
    private final int likeWeight;
    private final int viewWeight;
    private final int collectWeight;
    private final int commentWeight;

    // 通过@Value注入配置值
    public ContentHeatCalculator(
            @Value("${heat.calculator.gravity}") double gravity,
            @Value("${heat.calculator.like-weight}") int likeWeight,
            @Value("${heat.calculator.view-weight}") int viewWeight,
            @Value("${heat.calculator.collect-weight}") int collectWeight,
            @Value("${heat.calculator.comment-weight}") int commentWeight) {
        this.gravity = gravity;
        this.likeWeight = likeWeight;
        this.viewWeight = viewWeight;
        this.collectWeight = collectWeight;
        this.commentWeight = commentWeight;
    }

    private int calculateHeat(double W, double I, double T) {
        double heat = (W + I) / Math.pow(T + 1, gravity);
        return (int) Math.round(heat);
    }

    public int calculateHotIndex(double influence, double T, ArticleAnalyse W) {
        int w = (W.getLikeCount() * likeWeight) + (W.getViewCount() * viewWeight) +
                (W.getCollectCount() * collectWeight) + (W.getCommentCount() * commentWeight);
        return calculateHeat(w, influence, T);
    }

}
