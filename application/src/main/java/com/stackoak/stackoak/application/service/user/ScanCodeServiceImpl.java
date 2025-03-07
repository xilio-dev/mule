package com.stackoak.stackoak.application.service.user;

import com.stackoak.stackoak.common.data.auth.ScanCodeDTO;
import org.springframework.stereotype.Service;

@Service
public class ScanCodeServiceImpl implements IScanCodeService{
    /**
     * 获取登陆二维码信息签名
     *
     * @return 扫码信息
     */
    @Override
    public ScanCodeDTO qrCode() {
        ScanCodeDTO scanCodeDTO = new ScanCodeDTO();
        scanCodeDTO.setSign("4154565165");
        scanCodeDTO.setImgUrl("https://stackoverflow.com/images/api-key/sign=4154565165");
        return scanCodeDTO;
    }
}
