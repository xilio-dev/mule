package com.stackoak.stackoak.application.service.user;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.stackoak.stackoak.common.data.mail.EmailLoginDTO;
import com.stackoak.stackoak.common.data.mail.EmailRegisterDTO;
import org.springframework.stereotype.Service;

@Service("adminLoginService")
public class AdminLoginServiceImpl implements ILoginService{
    /**
     * 邮箱验证码登陆
     *
     * @param dto 登陆信息
     * @return 登陆结果
     */
    @Override
    public SaTokenInfo emailCodeLogin(EmailRegisterDTO dto) {
        return null;
    }

    /**
     * 邮箱账号密码登陆
     *
     * @param emailLoginDTO 登陆请求信息
     * @return 登陆结果
     */
    @Override
    public SaTokenInfo emailLogin(EmailLoginDTO emailLoginDTO) {
        return null;
    }

    /**
     * 绑定邮箱
     *
     * @param dto 绑定信息
     */
    @Override
    public void bindEmail(EmailRegisterDTO dto) {

    }

    /**
     * 改变邮箱绑定
     *
     * @param dto 新邮箱信息
     */
    @Override
    public void changeEmailBind(EmailRegisterDTO dto) {

    }
}
