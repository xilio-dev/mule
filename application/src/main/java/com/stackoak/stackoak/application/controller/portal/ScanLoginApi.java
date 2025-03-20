package com.stackoak.stackoak.application.controller.portal;

import com.stackoak.stackoak.application.actors.bean.DynamicBeanManager;

import com.stackoak.stackoak.application.service.user.IScanCodeLogingService;
import com.stackoak.stackoak.common.message.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("scan")
public class ScanLoginApi {
    @Autowired
    private IScanCodeLogingService scanCodeService;

    @GetMapping(value = "get_qr_code", name = "生成登陆二维码")
    public Result getQrCode() {
        return Result.success(scanCodeService.getQrCode());
    }

    @GetMapping(value = "qr_code", name = "通过sign获取登陆二维码")
    public Result qrCode(@RequestBody Map<String, Object> params) {
        return Result.success(scanCodeService.qrCode(params));
    }

    @PostMapping(value = "qr_login", name = "扫码登陆")
    public Result qrLogin(@RequestBody Map<String, Object> params) {
        return Result.success(scanCodeService.qrLogin(params));
    }

    @PostMapping(value = "setStatus", name = "设置状态")
    public Result setStatus(@RequestParam String qrInfo) {

        return Result.success(scanCodeService.setStatus(qrInfo));
    }

    @PostMapping(value = "confirm_login", name = "APP确认登陆")
    public Result confirmLogin(@RequestBody Map<String, Object> params) {
        scanCodeService.confirmLogin(params);
        return Result.success();
    }


    @PostMapping("check_status")
    public Result checkStatus(@RequestBody Map<String, Object> params) {
        return Result.success(scanCodeService.checkStatus(params));
    }
}
