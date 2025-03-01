package com.stackoka.stackoka.application.service.search;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoka.stackoka.common.data.article.Article;
import com.stackoka.stackoka.common.data.article.ArticleId;
import com.stackoka.stackoka.common.data.search.SearchRequest;

import java.util.List;

public interface ISearchService {
    /**
     * 保存文章索引
     *
     * @param article 文章信息
     * @return
     */
    public int saveIndex(Article article);

    public int deleteIndex(ArticleId articleId);

    public IPage fullTextSearch(String keyword, SearchRequest request);
}
