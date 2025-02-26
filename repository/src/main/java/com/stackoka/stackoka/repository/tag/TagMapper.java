package com.stackoka.stackoka.repository.tag;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stackoka.stackoka.common.data.tag.Tag;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stackoka.com
 * @since 2025-02-15
 */
public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> selectTagsByArticleId(String articleId);
}

