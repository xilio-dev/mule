package com.stackoak.stackoak.application.service.tag;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stackoak.stackoak.common.data.tag.Tag;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-15
 */
public interface ITagService extends IService<Tag> {

    List<Tag> getTagsByArticleId(String id);

    Tag getTagByName(String tagName);
}
