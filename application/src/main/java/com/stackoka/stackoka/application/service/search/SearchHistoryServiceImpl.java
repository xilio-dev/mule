package com.stackoka.stackoka.application.service.search;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoka.stackoka.common.data.search.SearchHistory;
import com.stackoka.stackoka.repository.search.SearchHistoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-04 12:31:46
 */
@Service
public class SearchHistoryServiceImpl extends ServiceImpl<SearchHistoryMapper, SearchHistory> implements ISearchHistoryService {

    /**
     * 获取用户搜索历史
     *
     * @return 搜索历史关键字
     */
    @Override
    public List<String> getUserSearchHistory() {
        String userId = "1";
        LambdaQueryWrapper<SearchHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SearchHistory::getUserId, userId);
        wrapper.orderByDesc(SearchHistory::getCreatedAt);
        Page<SearchHistory> page = Page.of(1, 10);
        List<SearchHistory> searchHistoryList = page(page, wrapper).getRecords();
        return searchHistoryList.stream()
                .map(SearchHistory::getKeyword)
                .toList();
    }
}
