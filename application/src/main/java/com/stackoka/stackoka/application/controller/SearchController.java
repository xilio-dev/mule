package com.stackoka.stackoka.application.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoka.stackoka.application.service.search.ISearchService;
import com.stackoka.stackoka.common.data.search.SearchRequest;
import com.stackoka.stackoka.common.message.RestResult;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SearchController {
    @Autowired
    private ISearchService searchService;

    @PostMapping("search")
    public RestResult search(@RequestBody @Valid SearchRequest request) {
        IPage page = searchService.fullTextSearch(request.getKeyword(), request);
        return RestResult.success(page);
    }
}
