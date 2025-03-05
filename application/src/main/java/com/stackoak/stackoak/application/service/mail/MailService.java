package com.stackoak.stackoak.application.service.mail;

import com.stackoak.stackoak.application.exception.BizException;
import com.stackoak.stackoak.common.data.mail.SendEmailDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * todo 后续抽取为公共服务
 */
@Component
public class MailService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private MailHelper mailHelper;

    /**
     * 发送验证码
     *
     * @param dto 邮箱信息
     */
    public void sendEmailValidCode(@Valid SendEmailDTO dto) {
        //检查是否已经发送了验证码，如果验证码还没有过期，通知用户不要频繁发送验证码
        String validCode = redisTemplate.opsForValue().get("stackoak:emailvalidcode:" + dto.getEmail());
        if (StringUtils.hasText(validCode)) {
            throw new BizException("验证码已成功发送，请3分钟后再试！");
        }
        //todo 增加每天发送验证码的次数
        Random random = new Random();
        int code = random.nextInt(9000) + 1000;

        //HTML 内容
        String content = "<html><body>";
        content += "<h1>您的 StackOak 登录验证码</h1>";
        content += "<p>尊敬的用户，您的 StackOak 登录验证码为：<b>" + code + "</b></p>";
        content += "<p>请不要将验证码泄露给他人。</p>";
        content += "<p>此邮件由系统自动发送，请勿直接回复。</p>";
        content += "</body></html>";
        // 发送邮件
        mailHelper.sendHtmlMail(dto.getEmail(), "stackoak网站登陆验证码", content);
        //将验证码存储到Redis缓存，设置过期时间为5分钟
        redisTemplate.opsForValue().set("stackoak:emailvalidcode:" + dto.getEmail(), String.valueOf(code),3, TimeUnit.MINUTES);
    }
}
