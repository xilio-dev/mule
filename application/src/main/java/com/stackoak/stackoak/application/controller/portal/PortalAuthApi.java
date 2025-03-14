package com.stackoak.stackoak.application.controller.portal;


import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.google.gson.Gson;
import com.stackoak.stackoak.application.service.user.ILoginService;
import com.stackoak.stackoak.common.data.mail.EmailLoginDTO;
import com.stackoak.stackoak.common.data.mail.EmailRegisterDTO;
import com.stackoak.stackoak.common.data.user.UpdatePwdRequest;
import com.stackoak.stackoak.common.message.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Tag(name = "认证")
@RestController
@RequestMapping("/auth")
public class PortalAuthApi {
    @Resource
    @Qualifier("userLoginService")
    private ILoginService loginService;

    @GetMapping("/login")
    public void login(@RequestParam String code, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String GITEE_ACCESS_TOKEN_URL = "https://gitee.com/oauth/token?grant_type=authorization_code&code=" + code + "&client_id=e6fa7f51960698ac3216d31543f73b1e83fae27296828d823525ff78f7c45c3c&redirect_uri=http://localhost:9856/auth/login&client_secret=46d924cafbeaff852f0545e8f816b1de2a6fa553bb653e0a26c5c8295e7c64e6";
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


    @PostMapping(value = "emailLogin", name = "邮箱密码登陆")
    public Result emailLogin(@RequestBody @Valid EmailLoginDTO emailLoginDTO) {
        return Result.success(loginService.emailLogin(emailLoginDTO));
    }

    @PostMapping(value = "emailCodeLogin",name = "邮箱验证码登陆")
    public Result emailCodeLogin(@RequestBody @Valid EmailRegisterDTO dto) {
        SaTokenInfo loginInfo= loginService.emailCodeLogin(dto);
        return Result.success(loginInfo);
    }

    @PutMapping(value = "change-email-bind",name = "邮箱绑定变更")
    public Result changeEmailBind(@RequestBody @Valid EmailRegisterDTO dto) {
        loginService.changeEmailBind(dto);
        return Result.success();
    }
    @PutMapping(value = "update_account_password",name = "更新账户密码")
    public Result updateAccountPassword(@RequestBody @Valid UpdatePwdRequest request) {
        loginService.updateAccountPassword(request);
        return Result.success();
    }
    @PostMapping(value = "bindEmail",name = "三方登陆绑定邮箱")
    public Result bindEmail(@RequestBody @Valid EmailRegisterDTO dto) {
         loginService.bindEmail(dto);
        return Result.success();
    }

    @PostMapping("logout")
    public Result logout() {
        StpUtil.logout();
        return Result.success();
    }
}
