package com.stackoak.stackoak.common.data.article;

import com.stackoak.stackoak.common.data.PageQuery;

/**
 *
 * @param pageQuery
 * @param cid 专栏ID
 */
public record ListByUserAndColumnQuery (PageQuery pageQuery, String cid){
}
