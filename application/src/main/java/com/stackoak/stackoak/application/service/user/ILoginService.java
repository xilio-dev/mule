package com.stackoak.stackoak.application.service.user;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.stackoak.stackoak.common.data.mail.EmailLoginDTO;
import com.stackoak.stackoak.common.data.mail.EmailRegisterDTO;
import jakarta.validation.Valid;

public interface ILoginService {
    /**
     * 邮箱验证码登陆
     * @param dto 登陆信息
     * @return 登陆结果
     */
    SaTokenInfo emailCodeLogin(@Valid EmailRegisterDTO dto);


    /**
     * 邮箱账号密码登陆
     *
     * @param emailLoginDTO 登陆请求信息
     * @return 登陆结果
     */
    SaTokenInfo emailLogin(@Valid EmailLoginDTO emailLoginDTO);
}
