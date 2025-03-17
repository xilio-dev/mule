package com.stackoak.stackoak.common.data.article;

import com.stackoak.stackoak.common.data.IPageQuery;
import com.stackoak.stackoak.common.data.PageQuery;

/**
 * 根据用户和分类获取文章列表
 *
 * @param cid
 */
public record ListByUserAndCategoryQuery(PageQuery pageQuery, String cid)     {

}
