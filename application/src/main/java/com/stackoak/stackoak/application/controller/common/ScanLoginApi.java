package com.stackoak.stackoak.application.controller.common;

import com.stackoak.stackoak.application.actors.bean.DynamicBeanManager;
import com.stackoak.stackoak.application.service.user.IScanCodeService;
import com.stackoak.stackoak.application.util.SpringHelper;
import com.stackoak.stackoak.common.message.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Function;

@RestController
@RequestMapping("scan")
public class ScanLoginApi {
    @Autowired
    private IScanCodeService scanCodeService;
@Autowired
private DynamicBeanManager dynamicBeanManager;
    @GetMapping("get-qr-code")
    public Result getQrCode() {
        return Result.success(scanCodeService.qrCode());
    }

    @PostMapping("qr-login")
    public Result qrLogin() {
        return Result.success(scanCodeService.qrCode());
    }

    @GetMapping("check-status")
    public Result checkStatus() {
        return Result.success(scanCodeService.qrCode());
    }
}
