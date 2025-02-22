package cn.xilio.project.controller;


import cn.xilio.project.domain.SendEmailDTO;
import cn.xilio.project.domain.mail.MailService;
import cn.xilio.project.domain.mail.VerificationCodeCache;
import cn.xilio.project.util.VerificationCodeGenerator;
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
