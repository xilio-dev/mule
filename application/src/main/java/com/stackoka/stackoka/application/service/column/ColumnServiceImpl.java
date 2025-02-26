package com.stackoka.stackoka.application.service.column;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stackoka.stackoka.common.data.column.Column;
import com.stackoka.stackoka.repository.column.ColumnMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author stackoka.com
 * @since 2025-02-23
 */
@Service
public class ColumnServiceImpl extends ServiceImpl<ColumnMapper, Column> implements IColumnService {

    @Autowired
    private ColumnMapper columnMapper;

    @Override
    public List<Column> getColumnsByArticleId(String id, String userId) {
        return columnMapper.selectColumnsByArticleId(id,userId);
    }

    @Override
    public Column getColumnByName(String columnName, String userId) {
        LambdaQueryWrapper<Column> wrapper = new LambdaQueryWrapper<Column>()
                .eq(Column::getName, columnName)
                .eq(Column::getUserId, userId);
        return getOne(wrapper);
    }
}
