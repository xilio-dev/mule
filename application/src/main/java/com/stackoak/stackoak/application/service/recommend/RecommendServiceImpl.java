package com.stackoak.stackoak.application.service.recommend;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.application.service.article.IArticleService;
import com.stackoak.stackoak.common.data.article.Article;
import com.stackoak.stackoak.common.data.article.ArticleBriefVO;
import com.stackoak.stackoak.common.data.recommend.RecommendByUserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommendServiceImpl implements IRecommendService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IArticleService articleService;
    // 基于用户的个性化推荐的文章推荐结果列表key
    private static final String RECOMMEND_ARTICLE_USER = "recommend:article:user:";

    /**
     * 基于用户进行个性化推荐
     * 推荐逻辑：
     * 1、如果是新用户，推荐热门文章
     * 2、如果是老用户，通过用户历史行为进行个性化推荐
     *
     * @param query 查询参数
     * @return 推荐列表
     */
    @Override
    public Page<ArticleBriefVO> homeRecommendByUserId(RecommendByUserQuery query) {
        final String redisKey = RECOMMEND_ARTICLE_USER + query.getUserId();
        Long current = query.getCurrent();
        Long size = query.getSize();
        // 计算分页索引（ZSet使用0-based索引）
        long start = (current - 1) * size;
        long end = start + size - 1;

        // todo 获取分页数据（按分数降序排列） 测试
        Set aids = redisTemplate.opsForZSet().reverseRange(redisKey, start, end);
        List<Article> list = articleService.listByIds(aids);
        Page<Article> page = Page.of(current, size);
        page.setRecords(list);
        page.setTotal(100);
        System.out.println("==>");
        for (Article o : list) {
            System.out.println("==>" + o.getId() + o.getTitle());
        }
        return null;
    }

}
