package com.stackoak.stackoak.application.controller.admin;


import com.stackoak.stackoak.application.service.mail.MailHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("email")
public class AdminEmailApi {
@Autowired
private MailHelper mailService;

}
