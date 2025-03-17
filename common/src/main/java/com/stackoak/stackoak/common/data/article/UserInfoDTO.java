package com.stackoak.stackoak.common.data.article;

import lombok.Data;

@Data
// 用户信息数据传输对象
public class UserInfoDTO {
    private String userId;
    private String nickname;
    private String avatar;
}
