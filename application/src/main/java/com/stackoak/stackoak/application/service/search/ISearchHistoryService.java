package com.stackoak.stackoak.application.service.search;


import com.baomidou.mybatisplus.extension.service.IService;
import com.stackoak.stackoak.common.data.search.SearchHistory;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-04 12:31:46
 */
public interface ISearchHistoryService extends IService<SearchHistory> {
    /**
     * 获取用户搜索历史
     * @return 搜索历史关键字
     */
    List<String> getUserSearchHistory(String userId);
    SearchHistory getSearchHistory(String userId,String keyword);

    /**
     * 清空用户搜索历史
     * @param userId 用户ID
     */
    void deleteSearchHistory(String userId);

}
