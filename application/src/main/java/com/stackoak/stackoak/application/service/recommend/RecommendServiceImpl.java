package com.stackoak.stackoak.application.service.recommend;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.application.service.article.IArticleService;
import com.stackoak.stackoak.common.data.CommonPageQuery;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.article.Article;
import com.stackoak.stackoak.common.data.article.ArticleBriefVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendServiceImpl implements IRecommendService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IArticleService articleService;

    @Override
    public Page<ArticleBriefVO> getRecommendation(String userId, CommonPageQuery pageQuery) {
        String category = pageQuery.getKeyword();
        Long page = pageQuery.getCurrent();
        Long size = pageQuery.getSize();
        //如果userId为空则实现默认推荐，否则进行个性化推荐
        String key = category != null ? "rec:user:" + userId + ":category:" + category : "rec:user:" + userId;
        long start = (page - 1) * size;
        long end = start + size - 1;
        List<String> articleIds = redisTemplate.opsForList().range(key, start, end);
        List<Article> articles = articleService.listByIds(articleIds);
        List<ArticleBriefVO> articleBriefVOS = new ArrayList<>();
        for (Article article : articles) {
            ArticleBriefVO vo = new ArticleBriefVO();
            vo.setId(article.getId());
            vo.setTitle(article.getTitle());
            vo.setUserId(article.getUserId());
            articleBriefVOS.add(vo);
        }
        Page<ArticleBriefVO> res = Page.of(page, size, articleBriefVOS.size());
        res.setRecords(articleBriefVOS);
        return res;
    }
}
