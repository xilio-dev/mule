package com.stackoak.stackoak.repository.collect;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.common.data.collect.Collect;
import com.stackoak.stackoak.common.data.collect.CollectDTO;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 * 收藏夹
 Mapper 接口
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-27 23:00:30
 */
public interface CollectMapper extends BaseMapper<Collect> {

    Page<CollectDTO> selectByUser(@Param("page") Page<CollectDTO> page, @Param("articleId") String articleId, @Param("userId") String userId);
}

