package com.stackoak.stackoak.application.controller.portal;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.application.service.search.ISearchHistoryService;
import com.stackoak.stackoak.application.service.search.ISearchService;
import com.stackoak.stackoak.common.data.search.SearchRequest;
import com.stackoak.stackoak.common.message.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Tag(name = "全文搜索")
@RestController
@RequestMapping("/")
public class PortalSearchApi {
    @Autowired
    private ISearchService searchService;
    @Autowired
    private ISearchHistoryService searchHistoryService;

    @PostMapping("search")
    public Result search(@RequestBody @Valid SearchRequest request) throws IOException {
        Integer type = request.getType();
        Page page = searchService.fullTextSearch(request.getKeyword(), request);
        return Result.success(page);
    }

    @GetMapping(value = "get_search_his", name = "获取用户搜索历史")
    public Result getSearchHistory() {
        String userId = StpKit.USER.getLoginIdAsString();
        List<String> history = searchHistoryService.getUserSearchHistory(userId);
        return Result.success(history);
    }

    @DeleteMapping(value = "del_search_his", name = "清空搜索历史")
    public Result deleteSearchHistory() {
        searchHistoryService.deleteSearchHistory(StpKit.USER.getLoginIdAsString());
        return Result.success();
    }
}
