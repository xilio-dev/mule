package com.stackoak.stackoak.common.data.user;

/**
 * @Author: stackoak.com
 * @Date: 2022/10/21 10:39
 * @param cover
 * @param type 1-背景图 2-主页顶部封面
 */
public record UpdateCoverRequest(String cover,Integer type) {
}
