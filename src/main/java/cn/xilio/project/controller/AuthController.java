package cn.xilio.project.controller;


import cn.xilio.project.common.Result;
import cn.xilio.project.domain.EmailLoginDTO;
import cn.xilio.project.domain.EmailRegisterDTO;
import cn.xilio.project.domain.mail.VerificationCodeCache;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private VerificationCodeCache verificationCodeCache;


    @GetMapping("/login")
    public void login(@RequestParam String code, HttpServletRequest request, HttpServletResponse response) throws IOException {


        String GITEE_ACCESS_TOKEN_URL = "https://gitee.com/oauth/token?grant_type=authorization_code&code="+code+"&client_id=e6fa7f51960698ac3216d31543f73b1e83fae27296828d823525ff78f7c45c3c&redirect_uri=http://localhost:9856/auth/login&client_secret=46d924cafbeaff852f0545e8f816b1de2a6fa553bb653e0a26c5c8295e7c64e6";
        // 请求参数
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "authorization_code");
        params.put("code", code);  // 用户授权码
        params.put("client_id", "e6fa7f51960698ac3216d31543f73b1e83fae27296828d823525ff78f7c45c3c");      // 客户端 ID
        params.put("redirect_uri", "http://localhost:9856/auth/login");// 回调地址
        params.put("client_secret", "46d924cafbeaff852f0545e8f816b1de2a6fa553bb653e0a26c5c8295e7c64e6"); // 客户端密钥


        String json = new Gson().toJson(params);


        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(GITEE_ACCESS_TOKEN_URL);
        httpPost.setEntity(new StringEntity(json));
        CloseableHttpResponse execute = httpClient.execute(httpPost);
        org.apache.http.HttpEntity entity = execute.getEntity();
        String string = EntityUtils.toString(entity);


    }



    @PostMapping("emailLogin")
    public Result emailLogin(@RequestBody @Valid EmailLoginDTO emailLoginDTO) {
        if (!ObjectUtils.isEmpty(emailLoginDTO)) {
            String email = emailLoginDTO.getEmail();
            String password = emailLoginDTO.getPassword();
            if ("StackOak@163.com".equals(email) && "123456".equals(password)) {
                UUID uuid = UUID.randomUUID();
                HashMap<String, Object> res = new HashMap<>();
                res.put("token", uuid.toString());
                return Result.success(res);
            }
        }
        return Result.error();
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
