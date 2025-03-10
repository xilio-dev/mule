package com.stackoak.stackoak.application.service.tag;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.tag.Tag;
import com.stackoak.stackoak.common.data.tag.TagTypeEnum;
import com.stackoak.stackoak.repository.tag.TagMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
 * @since 2025-02-15
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<Tag> getTagsByArticleId(String articleId) {
        return tagMapper.selectTagsByArticleId(articleId);
    }

    @Override
    public Tag getTagByName(String tagName) {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tag::getName, tagName);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public Page<Tag> sysTags(PageQuery query) {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tag::getType, TagTypeEnum.SYSTEM.getType());
        return page(query.getPage(),wrapper);
    }
}
