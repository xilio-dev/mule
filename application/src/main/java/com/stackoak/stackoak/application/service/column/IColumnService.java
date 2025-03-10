package com.stackoak.stackoak.application.service.column;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.column.Column;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-23
 */
public interface IColumnService extends IService<Column> {

    List<Column> getColumnsByArticleId(String id,String userId);

    Column getColumnByName(String columnName,String userId);

    /**
     * 获取用户的专栏，如果是专栏本人，可以看到所有，其他人只能看到开放的
     * @return 专栏列表
     */
    IPage<Column> getUserColumns(PageQuery pageQuery);

}
