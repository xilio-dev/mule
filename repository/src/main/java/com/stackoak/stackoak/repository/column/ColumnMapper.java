package com.stackoak.stackoak.repository.column;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.common.data.column.Column;
import com.stackoak.stackoak.common.data.user.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-23
 */
public interface ColumnMapper extends BaseMapper<Column> {

    List<Column> selectColumnsByArticleId(@Param("articleId") String articleId, @Param("userId") String userId);

    /**
     * 查询专栏的订阅者
     *
     * @param columnId 专栏id
     * @return 订阅者列表
     */

    Page<User> findSubscribersByColumnId(@Param("page") Page page, @Param("columnId") String columnId);

    /**
     * 查询我订阅的专栏列表
     *
     * @param page   分页参数
     * @param userId 用户id
     * @param name   专栏名称：like查询
     * @return 专栏列表
     */

    Page<Column> findSubscribedColumnsByUserId(@Param("page") Page page, @Param("userId") String userId, @Param("name") String name);
}

