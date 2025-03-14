package com.stackoak.stackoak.application.controller.admin;

import com.stackoak.stackoak.application.service.search.ISearchHistoryService;
import com.stackoak.stackoak.application.service.search.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class AdminSearchApi {
    @Autowired
    private ISearchService searchService;
    @Autowired
    private ISearchHistoryService searchHistoryService;

}
