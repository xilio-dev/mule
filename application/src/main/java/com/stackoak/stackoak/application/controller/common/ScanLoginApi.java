package com.stackoak.stackoak.application.controller.common;

import com.stackoak.stackoak.application.service.user.IScanCodeService;
import com.stackoak.stackoak.common.message.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("scan")
public class ScanLoginApi {
    @Autowired
    private IScanCodeService scanCodeService;

    @GetMapping("get-qr-code")
    public RestResult getQrCode() {
        return RestResult.success(scanCodeService.qrCode());
    }

    @PostMapping("qr-login")
    public RestResult qrLogin() {
        return RestResult.success(scanCodeService.qrCode());
    }

    @GetMapping("check-status")
    public RestResult checkStatus() {
        return RestResult.success(scanCodeService.qrCode());
    }
}
