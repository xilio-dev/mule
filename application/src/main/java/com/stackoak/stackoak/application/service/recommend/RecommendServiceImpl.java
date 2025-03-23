package com.stackoak.stackoak.application.service.recommend;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.application.service.article.IArticleService;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.article.Article;
import com.stackoak.stackoak.common.data.article.ArticleBriefVO;
import com.stackoak.stackoak.common.data.recommend.RecommendByUserQuery;
import com.stackoak.stackoak.common.data.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
    public Page<ArticleBriefVO> getHomeArticleRecommend(PageQuery query) {
        //如果是登陆用户，根据登陆用户的喜好进行推荐
        String userId = StpKit.USER.getLoginIdAsString();
        final String redisKey = RECOMMEND_ARTICLE_USER + userId;
        Long current = query.getCurrent();
        Long size = query.getSize();
        // 计算分页索引（ZSet使用0-based索引）
        long start = (current - 1) * size;
        long end = start + size - 1;

        // todo 获取分页数据（按分数降序排列） 测试
        Set aids = redisTemplate.opsForZSet().reverseRange(redisKey, start, end);
        if (aids == null || aids.isEmpty()) {
            return null;
        }
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

    /**
     * 首页作者推荐
     *
     * @param query 查询参数
     * @return 推荐作者列表
     */
    @Override
    public Page<User> homeAuthorRecommendByUserId(RecommendByUserQuery query) {

        return null;
    }

    /**
     * 获取相关文章
     * 推荐逻辑：
     * 1、如果当前用户已经登陆，则基于：当前用户行为 + 文章内容
     * 2、如果当前用户未登录，则基于：文章内容相关性推荐
     *
     * @param query 查询参数
     * @return 推荐列表
     */
    @Override
    public Page<ArticleBriefVO> getRelatedArticles(RecommendByUserQuery query) {
        return null;
    }

}
