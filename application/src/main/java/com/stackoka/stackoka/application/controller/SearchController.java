package com.stackoka.stackoka.application.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoka.stackoka.application.service.search.ISearchHistoryService;
import com.stackoka.stackoka.application.service.search.ISearchService;
import com.stackoka.stackoka.common.data.search.SearchHistory;
import com.stackoka.stackoka.common.data.search.SearchRequest;
import com.stackoka.stackoka.common.message.RestResult;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class SearchController {
    @Autowired
    private ISearchService searchService;
    @Autowired
    private ISearchHistoryService searchHistoryService;

    @PostMapping("search")
    public RestResult search(@RequestBody @Valid SearchRequest request) {
        IPage page = searchService.fullTextSearch(request.getKeyword(), request);
        return RestResult.success(page);
    }
    @GetMapping(value = "get_search_his",name = "获取用户搜索历史")
    public RestResult getSearchHistory() {
     List<String> history= searchHistoryService.getUserSearchHistory();
      return   RestResult.success(history);
    }
}
