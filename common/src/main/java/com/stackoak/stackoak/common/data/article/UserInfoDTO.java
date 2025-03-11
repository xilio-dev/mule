package com.stackoak.stackoak.common.data.article;

import lombok.Data;

@Data
// 用户信息数据传输对象
public class UserInfoDTO {
    // 用户ID
    private String userId;
    // 昵称
    private String nickname;
    // 头像
    private String avatar;

}
