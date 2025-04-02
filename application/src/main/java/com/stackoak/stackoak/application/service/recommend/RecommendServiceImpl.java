package com.stackoak.stackoak.application.service.recommend;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.application.service.article.IArticleService;
import com.stackoak.stackoak.common.data.CommonPageQuery;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.article.Article;
import com.stackoak.stackoak.common.data.article.ArticleBriefVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        if (articleIds.size()<1){
            return Page.of(page,size,0);
        }
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

    @Override
    public Page<ArticleBriefVO> getArticleSimilarityRecommender(String userId, CommonPageQuery pageQuery) {
        Long page = pageQuery.getCurrent();
        Long size = pageQuery.getSize();
        String articleId = pageQuery.getId();

        // 初始化分页对象
        Page<ArticleBriefVO> res = new Page<>(page, size);

        // 从 Redis 获取相似文章 ID 列表
        String redisKey = "article:recommend:" + articleId;
        String similarIdsStr = (String) redisTemplate.opsForValue().get(redisKey);

        if (similarIdsStr == null || similarIdsStr.isEmpty()) {
            System.out.println("No similar articles found in Redis for articleId: " + articleId);
            res.setRecords(Collections.emptyList());
            res.setTotal(0);
            return res;
        }

        // 将逗号分隔的 ID 字符串转换为 List
        List<String> similarIds = List.of(similarIdsStr.split(","));

        // 计算分页参数
        int start = (int) ((page - 1) * size);
        int end = (int) Math.min(start + size, similarIds.size());

        if (start >= similarIds.size()) {
            res.setRecords(Collections.emptyList());
            res.setTotal(similarIds.size());
            return res;
        }

        // 获取当前页的 ID 子集
        List<String> pageIds = similarIds.subList(start, end);

        // 从数据库查询文章详情
        List<Article> articles = articleService.listByIds(pageIds);
        if (articles == null || articles.isEmpty()) {
            System.out.println("No articles found in database for IDs: " + pageIds);
            res.setRecords(Collections.emptyList());
            res.setTotal(similarIds.size());
            return res;
        }

        // 转换为 VO
        List<ArticleBriefVO> articleVOs = articles.stream()
                .map(article -> {
                    ArticleBriefVO vo = new ArticleBriefVO();
                    BeanUtils.copyProperties(article,vo);
                    return vo;
                })
                .collect(Collectors.toList());

        // 设置分页结果
        res.setRecords(articleVOs);
        res.setTotal(similarIds.size());

        return res;
    }
}
