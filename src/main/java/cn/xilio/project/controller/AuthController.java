package cn.xilio.project.controller;


import cn.xilio.project.domain.EmailLoginDTO;
import cn.xilio.project.domain.EmailRegisterDTO;
import cn.xilio.project.domain.mail.VerificationCodeCache;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private VerificationCodeCache verificationCodeCache;
    @GetMapping("login")
    public String login(String code) {
        System.out.println(code);
        return "ok";
    }

    @PostMapping("emailLogin")
    public Object emailLogin(@RequestBody @Valid EmailLoginDTO emailLoginDTO) {
        if (!ObjectUtils.isEmpty(emailLoginDTO)) {
            String email = emailLoginDTO.getEmail();
            String password = emailLoginDTO.getPassword();
            if ("StackOak@163.com".equals(email) && "123456".equals(password)) {
                UUID uuid = UUID.randomUUID();
                HashMap<String, Object> res = new HashMap<>();
                res.put("token", uuid.toString());
                return res;
            }
        }
        return "401";
    }
    @PostMapping("emailCodeLogin")
    public Object emailCodeLogin(@RequestBody @Valid EmailRegisterDTO emailRegisterDTO) {
        String email = emailRegisterDTO.getEmail();
        Integer inputCode = emailRegisterDTO.getCode();
        // 检查验证码是否正确
        String storedCode = verificationCodeCache.get(email);
        if (storedCode == null || !storedCode.equals(String.valueOf(inputCode))) {
            // 验证码错误或不存在
            return new ResponseEntity<>("Invalid verification code", HttpStatus.UNAUTHORIZED);
        }

        // 验证码正确，执行登录逻辑
        // 清除验证码（防止重复使用）
        verificationCodeCache.verifyAndRemove(email,String.valueOf(inputCode));
        // 返回登录成功信息
        return new ResponseEntity<>("Login successful", HttpStatus.OK);
    }
}
