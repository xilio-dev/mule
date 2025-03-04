package com.stackoak.stackoak.repository.follow;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.common.data.follow.FollowUserVO;
import com.stackoak.stackoak.common.data.follow.Follows;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-04 14:53:49
 */
public interface FollowsMapper extends BaseMapper<Follows> {
    /**
     * 查询我的粉丝
     *
     * @param page   分页参数
     * @param userId 我的编号
     * @return 粉丝列表
     */
    public IPage<FollowUserVO> selectFans(@Param("page") Page<FollowUserVO> page, @Param("userId") String userId, @Param("visitorUserId")String visitorUserId);

    /**
     * 查询我关注的人
     *
     * @param page   分页参数
     * @param userId 我的编号
     * @return 关注列表
     */
    public List<FollowUserVO> selectFollow(@Param("page") Page<FollowUserVO> page, @Param("userId") String userId, @Param("visitorUserId")String visitorUserId);

}

