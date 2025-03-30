package com.stackoak.stackoak.application.service.search;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.common.data.article.Article;
import com.stackoak.stackoak.common.data.article.ArticleId;
import com.stackoak.stackoak.common.data.search.SearchRequest;

import java.io.IOException;

public interface ISearchService {
    /**
     * 保存文章索引
     *
     * @param article 文章信息
     * @return
     */
    public int saveIndex(Article article);

    public int deleteIndex(ArticleId articleId);

    public Page fullTextSearch(String keyword, SearchRequest request) throws IOException;
}
