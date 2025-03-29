package com.stackoak.stackoak.application.service.column;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.application.exception.BizException;
import com.stackoak.stackoak.common.data.CommonPageQuery;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.column.*;
import com.stackoak.stackoak.common.data.user.User;
import com.stackoak.stackoak.repository.column.ArticleColumnMapper;
import com.stackoak.stackoak.repository.column.ColumnMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoak.stackoak.repository.column.ColumnSubscriptionMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

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
    @Autowired
    private ArticleColumnMapper articleColumnMapper;
    @Autowired
    private ColumnSubscriptionMapper columnSubscribeMapper;

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


    public Page<Column> getUserColumns(PageQuery query,String userId) {
        LambdaQueryWrapper<Column> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Column::getUserId, userId);
        wrapper.eq(Column::getStatus, 1);
        return page(Page.of(query.getCurrent(), query.getSize()), wrapper);
    }

    @Override
    public Page<Column> listByUser(ListColumnByUserQuery request) {
        String key = request.getKey();
        Integer status = request.getStatus();
        LambdaQueryWrapper<Column> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(-1 != status, Column::getStatus, status);
        wrapper.eq(Column::getUserId, StpKit.USER.getLoginIdAsString());
        wrapper.like(StringUtils.hasText(key), Column::getName, key);
        return page(Page.of(request.getCurrent(), request.getSize()), wrapper);
    }

    @Override
    public Page<User> subscribeToMe(CommonPageQuery query) {
        return columnMapper.findSubscribersByColumnId(Page.of(query.getCurrent(), query.getSize()), query.getId());
    }

    @Override
    public Page<Column> subscribeFromMe(CommonPageQuery query) {
        return columnMapper.findSubscribedColumnsByUserId(Page.of(query.getCurrent(), query.getSize()), StpKit.USER.getLoginIdAsString(), query.getKeyword());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteColumn(String columnId) {
        String userId = StpKit.USER.getLoginIdAsString();
        LambdaQueryWrapper<Column> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Column::getId, columnId);
        wrapper.eq(Column::getUserId, userId);
        remove(wrapper);/*删除专栏*/
        //删除专栏，需要解除专栏与文章的关联
        LambdaQueryWrapper<ArticleColumn> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.eq(ArticleColumn::getColumnId, columnId);
        articleColumnMapper.delete(wrapper2);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdate(ColumnSaveRequest body) {
        String cid = body.getId();
        //没有ID，新增专栏,新增专栏的时候需要判断是否已经存在相同名称的专栏
        if (!StringUtils.hasText(cid)) {
            Column column = new Column();
            column.setName(body.getName());
            column.setCover(body.getCover());
            column.setIntro(body.getIntro());
            column.setUserId(StpKit.USER.getLoginIdAsString());
            //检查是否已经存在专栏名字了
            Column columnByName = getColumnByName(body.getName(), StpKit.USER.getLoginIdAsString());
            if (!ObjectUtils.isEmpty(columnByName)) {
                throw new BizException("专栏名字已存在，请换一个！");
            }
            save(column);
        } else {
            //编辑专栏
            Column oldColumn = getById(cid);
            if (ObjectUtils.isEmpty(oldColumn)) {
                throw new BizException("专栏不存在！");
            }
            Column columnByName = getColumnByName(body.getName(), StpKit.USER.getLoginIdAsString());
            if (!ObjectUtils.isEmpty(columnByName) && !columnByName.getId().equals(cid)) {
                throw new BizException("专栏名字已存在，请换一个！");
            }
            oldColumn.setId(oldColumn.getId());
            oldColumn.setName(body.getName());
            oldColumn.setCover(body.getCover());
            oldColumn.setIntro(body.getIntro());
            updateById(oldColumn);
        }
    }

    @Override
    public ColumnDetailVo detail(String  cid) {
        return baseMapper.selectColumnDetailByUserId(cid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void subscribe(SubscribeRequest request, String userId) {
        //检查被订阅的专栏是否存在
        Column column = getById(request.columnId());
        BizException.noNull(column, "专栏不存在！");
        //检查是否已经订阅过
        LambdaQueryWrapper<ColumnSubscription> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ColumnSubscription::getColumnId, request.columnId());
        wrapper.eq(ColumnSubscription::getUserId, userId);
        ColumnSubscription columnSubscription = columnSubscribeMapper.selectOne(wrapper);
        BizException.exprNull(!ObjectUtils.isEmpty(columnSubscription), "已经订阅过了！");
        ColumnSubscription newSub = ColumnSubscription.builder().columnId(request.columnId()).userId(userId).build();
        columnSubscribeMapper.insert(newSub);
    }
    @Override
    public void cancelSubscribe(SubscribeRequest request, String userId) {
        LambdaQueryWrapper<ColumnSubscription> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ColumnSubscription::getColumnId, request.columnId());
        wrapper.eq(ColumnSubscription::getUserId, userId);
        columnSubscribeMapper.delete(wrapper);
    }
}
