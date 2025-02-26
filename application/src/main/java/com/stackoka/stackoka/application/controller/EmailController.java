package com.stackoka.stackoka.application.controller;


import com.stackoka.stackoka.common.data.mail.SendEmailDTO;
import com.stackoka.stackoka.common.data.mail.MailService;
import com.stackoka.stackoka.application.util.VerificationCodeGenerator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("email")
public class EmailController {
@Autowired
private MailService mailService;
    @PostMapping("send")
    public void sendEmail(@RequestBody @Valid SendEmailDTO sendEmailDTO) {
        String code = VerificationCodeGenerator.generateVerificationCode();
        mailService.sendHtmlMailWithCode(sendEmailDTO.getEmail(), "StackOak登陆验证码",code);
        System.out.println(sendEmailDTO);
    }
}
