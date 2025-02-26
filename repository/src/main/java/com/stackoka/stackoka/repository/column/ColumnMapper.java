package com.stackoka.stackoka.repository.column;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stackoka.stackoka.common.data.column.Column;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author stackoka.com
 * @since 2025-02-23
 */
public interface ColumnMapper extends BaseMapper<Column> {

    List<Column> selectColumnsByArticleId(@Param("articleId") String articleId, @Param("userId") String userId);
}

