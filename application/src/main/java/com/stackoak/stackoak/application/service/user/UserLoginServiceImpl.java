package com.stackoak.stackoak.application.service.user;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.stackoak.stackoak.application.actors.security.SecureManager;
import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.application.exception.BizException;
import com.stackoak.stackoak.common.data.mail.EmailLoginDTO;
import com.stackoak.stackoak.common.data.mail.EmailRegisterDTO;
import com.stackoak.stackoak.common.data.user.User;
import com.stackoak.stackoak.common.data.user.UserStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service("userLoginService")
public class UserLoginServiceImpl implements ILoginService {
    @Autowired
    private IUserService userService;
    @Autowired
    private SecureManager secureManager;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 邮箱验证码登陆
     *
     * @param dto 登陆信息
     * @return 登陆结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SaTokenInfo emailCodeLogin(EmailRegisterDTO dto) {
        //检查验证码是否正确 从缓存获取验证码
        String emailCode = redisTemplate.opsForValue().get("stackoak:emailvalidcode:" + dto.getEmail());
        if (!dto.getCode().equalsIgnoreCase(emailCode)) {
            throw new BizException("验证码不正确或已过期！");
        }
        //验证码正确，执行登录逻辑
        //如果用户已经注册过了，直接返回登陆信息
        User user = userService.getByEmail(dto.getEmail());
        String userId;
        if (!ObjectUtils.isEmpty(user)) {
            userId = user.getId();
        } else {
            //如果用户没有注册，自动创建一个新的账号
            User newUser = new User();
            newUser.setEmail(dto.getEmail());
            newUser.setStatus(UserStatusEnum.NORMAL.getStatus());
            newUser.setUsername(dto.getEmail());
            userService.save(newUser);
            userId = newUser.getId();
        }
        StpKit.USER.login(userId, new SaLoginModel()
                .setDevice("PC")
                .setIsLastingCookie(true)
                .setTimeout(60 * 60 * 24 * 7)
                .setIsWriteHeader(true));
        //清除验证码（防止重复使用）
        redisTemplate.delete("stackoak:emailvalidcode:" + dto.getEmail());
        //返回登录成功信息
        return StpUtil.getTokenInfo();
    }

    /**
     * 邮箱账号密码登陆
     *
     * @param dto 登陆请求信息
     * @return 登陆结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SaTokenInfo emailLogin(EmailLoginDTO dto) {
        //todo 密码输入次数检查，超过10次24小时以后才能登陆
        User user = userService.getByEmail(dto.getEmail());
        if (ObjectUtils.isEmpty(user)) {
            throw new BizException("账户尚未注册！");
        }
        if (!StringUtils.hasText(user.getPassword())){
            throw new BizException("未设置密码，请用验证码登陆！");
        }
        if (UserStatusEnum.BLOCKED.getStatus().intValue() == user.getStatus().intValue()) {
            throw new BizException("账号被封禁");
        }
        if (!secureManager.decrypt(user.getPassword()).equals(dto.getPassword())) {
            throw new BizException("密码不正确");
        }
        StpKit.USER.login(user.getId(), new SaLoginModel()
                .setDevice("PC")// 此次登录的客户端设备类型, 用于[同端互斥登录]时指定此次登录的设备类型
                .setIsLastingCookie(true)// 是否为持久Cookie（临时Cookie在浏览器关闭时会自动删除，持久Cookie在重新打开后依然存在）
                .setTimeout(60 * 60 * 24 * 7)// 指定此次登录token的有效期, 单位:秒 （如未指定，自动取全局配置的 timeout 值）
                .setIsWriteHeader(true)// 是否在登录后将 Token 写入到响应头
        );
        //todo 登陆日志记录
        return StpUtil.getTokenInfo();
    }
}
