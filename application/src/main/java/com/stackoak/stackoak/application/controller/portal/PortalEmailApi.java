package com.stackoak.stackoak.application.controller.portal;

import com.stackoak.stackoak.application.service.mail.MailService;
import com.stackoak.stackoak.common.data.mail.EmailRegisterDTO;
import com.stackoak.stackoak.common.data.mail.SendEmailDTO;
import com.stackoak.stackoak.common.message.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "邮件")
@RestController
@RequestMapping("/email")
public class PortalEmailApi {
@Autowired
private MailService mailService;

    @PostMapping(value = "send",name = "发送验证码")
    public Result sendEmail(@RequestBody @Valid SendEmailDTO dto) {
        mailService.sendEmailValidCode(dto);
        return Result.success();
    }

    @PostMapping(value = "valid-email",name = "验证邮箱")
    public Result validEmail(@RequestBody @Valid EmailRegisterDTO dto) {
        boolean valid=mailService.validEmail(dto);
        return Result.success(valid);
    }
}
