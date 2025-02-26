package com.stackoka.stackoka.application.service.column;


import com.baomidou.mybatisplus.extension.service.IService;
import com.stackoka.stackoka.common.data.column.Column;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stackoka.com
 * @since 2025-02-23
 */
public interface IColumnService extends IService<Column> {

    List<Column> getColumnsByArticleId(String id,String userId);

    Column getColumnByName(String columnName,String userId);

}
