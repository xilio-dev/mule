package com.stackoak.stackoak.common.data.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailProperties mailProperties;
    @Autowired
    private VerificationCodeCache verificationCodeCache;

    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(mailProperties.getUsername());
        message.setSubject(subject);
        message.setText(content);
        this.javaMailSender.send(message);
    }

    /**
     * 发送包含验证码的 HTML 邮件
     *
     * @param to      收件人邮箱地址
     * @param subject 邮件主题
     * @param code    验证码
     */
    public void sendHtmlMailWithCode(String to, String subject, String code) {
        // 定义 HTML 内容
        String content = "<html><body>";
        content += "<h1>您的 StackOak 登录验证码</h1>";
        content += "<p>尊敬的用户，您的 StackOak 登录验证码为：<b>" + code + "</b></p>";
        content += "<p>请不要将验证码泄露给他人。</p>";
        content += "<p>此邮件由系统自动发送，请勿直接回复。</p>";
        content += "</body></html>";
        // 发送邮件
        verificationCodeCache.put(to, code, 600);
        sendHtmlMail(to, subject, content);

    }

    private void sendHtmlMail(String to, String subject, String content) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(mailProperties.getUsername());
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, true); // 设置为 HTML 格式

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
