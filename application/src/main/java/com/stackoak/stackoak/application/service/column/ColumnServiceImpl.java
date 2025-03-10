package com.stackoak.stackoak.application.service.column;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.column.Column;
import com.stackoak.stackoak.repository.column.ColumnMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-23
 */
@Service
public class ColumnServiceImpl extends ServiceImpl<ColumnMapper, Column> implements IColumnService {

    @Autowired
    private ColumnMapper columnMapper;

    @Override
    public List<Column> getColumnsByArticleId(String id, String userId) {
        return columnMapper.selectColumnsByArticleId(id, userId);
    }

    @Override
    public Column getColumnByName(String columnName, String userId) {
        LambdaQueryWrapper<Column> wrapper = new LambdaQueryWrapper<Column>()
                .eq(Column::getName, columnName)
                .eq(Column::getUserId, userId);
        return getOne(wrapper);
    }


    public Page getUserColumns(PageQuery query) {
        LambdaQueryWrapper<Column> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Column::getUserId, StpKit.USER.getLoginIdAsString());
        return page(query.getPage(), wrapper);
    }
}
