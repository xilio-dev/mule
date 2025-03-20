package com.stackoak.stackoak.application.service.user;

import com.stackoak.stackoak.common.data.auth.QrCodeDTO;


import java.util.Map;

/**
 * 扫码登陆服务
 */
public interface IScanCodeLogingService {
    /**
     * 获取登陆二维码信息签名
     *
     * @return 扫码信息
     */
    public QrCodeDTO getQrCode();

    Object qrLogin(Map<String, Object> params);

    Object checkStatus(Map<String, Object> params);

    Object confirmLogin(Map<String, Object> params);

    Object setStatus(Map<String, Object> params);

    Object qrCode(Map<String, Object> params);
}
