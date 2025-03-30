package com.stackoak.stackoak.application.service.search;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoak.stackoak.common.data.search.SearchHistory;
import com.stackoak.stackoak.repository.search.SearchHistoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<String> getUserSearchHistory(String userId) {
        LambdaQueryWrapper<SearchHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SearchHistory::getUserId, userId);
        wrapper.orderByDesc(SearchHistory::getCreatedAt);
        Page<SearchHistory> page = Page.of(1, 20);
        List<SearchHistory> searchHistoryList = page(page, wrapper).getRecords();
        return searchHistoryList.stream()
                .map(SearchHistory::getKeyword)
                .toList();
    }

    @Override
    public SearchHistory getSearchHistory(String userId, String keyword) {
        LambdaQueryWrapper<SearchHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SearchHistory::getUserId, userId);
        wrapper.eq(SearchHistory::getKeyword, keyword);
        return getOne(wrapper);
    }

    /**
     * 清空用户搜索历史
     *
     * @param userId 用户ID
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteSearchHistory(String userId) {
        LambdaQueryWrapper<SearchHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SearchHistory::getUserId, userId);
        remove(wrapper);
    }
}
