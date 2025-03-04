package com.stackoak.stackoak.application.config;

import com.stackoak.stackoak.application.service.article.IArticleService;
import com.stackoak.stackoak.application.service.search.ISearchService;
import com.stackoak.stackoak.common.data.article.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;

@Component
public class LoadLuceneIndex implements CommandLineRunner  {
    @Autowired
    private ISearchService searchService;
    @Autowired
    private IArticleService articleService;
    /**
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println("delete lucene index");
        File file = new File("store/lucene/index");
        file.deleteOnExit();
        System.out.println("Loading lucene index...");
        List<Article> list = articleService.list();
        for (Article article : list) {
            if (!StringUtils.hasText(article.getId())){
                System.out.println("文章编号为空！");
                continue;
            }
            if (!StringUtils.hasText(article.getTitle())){
                continue;
            }
            if (!StringUtils.hasText(article.getDescription())){
                continue;
            }
            if (!StringUtils.hasText(article.getContent())){
                continue;
            }
            searchService.saveIndex(article);
        }
        System.out.println("Finished loading lucene index");
    }
}
