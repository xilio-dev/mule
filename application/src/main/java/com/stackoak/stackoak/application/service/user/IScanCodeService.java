package com.stackoak.stackoak.application.service.user;

import com.stackoak.stackoak.common.data.auth.ScanCodeDTO;

/**
 * 扫码登陆服务
 */
public interface IScanCodeService {
    /**
     * 获取登陆二维码信息签名
     * @return 扫码信息
     */
    public ScanCodeDTO qrCode();
}
