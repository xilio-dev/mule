package com.stackoak.stackoak.repository.tag;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stackoak.stackoak.common.data.tag.Tag;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-15
 */
public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> selectTagsByArticleId(String articleId);
}

