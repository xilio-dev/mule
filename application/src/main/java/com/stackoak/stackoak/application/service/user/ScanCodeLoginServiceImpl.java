package com.stackoak.stackoak.application.service.user;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.application.config.StackOakConfig;
import com.stackoak.stackoak.application.exception.BizException;
import com.stackoak.stackoak.application.util.AlgUtils;
import com.stackoak.stackoak.application.util.FileUtils;
import com.stackoak.stackoak.application.util.HttpUtils;
import com.stackoak.stackoak.common.data.Constants;
import com.stackoak.stackoak.common.data.auth.QrCodeDTO;
import com.stackoak.stackoak.common.data.auth.ScanCodeStatus;
import com.stackoak.stackoak.common.data.user.User;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ScanCodeLoginServiceImpl implements IScanCodeLogingService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IUserService userService;

    private final String QR_CODE_PREFIX = "qrcode:";
    private final String QR_CODE_STATUS_PREFIX = "qrcodestatus:";
    private final String QR_CODE_SECRIT_PREFIX = "qrcodesecrit:";
    private final String QR_CODE_USER_PREFIX = "qrcodeuser:";

    /**
     * 获取登陆二维码信息签名
     *
     * @return 扫码信息
     */
    @Override
    public QrCodeDTO getQrCode() {
        String server = "192.168.0.151:9856/portal";
        String sign = UUID.randomUUID().toString().replaceAll("-", "") + System.currentTimeMillis();
        try {
            String absPath = FileUtils.getAbsoluteFile(StackOakConfig.getUploadPath(), sign + ".png").getAbsolutePath();

            String imgUrl = FileUtils.getPathFileName(StackOakConfig.getUploadPath(), sign + ".png");
            String qrCodeText = server + imgUrl + "?sign=" + sign;
            QRCode.from(qrCodeText).to(ImageType.PNG).writeTo(new FileOutputStream(absPath));

            //将二维码存储到redis，并设置过期时间
            redisTemplate.opsForValue().set(QR_CODE_PREFIX + sign, qrCodeText);
            redisTemplate.opsForValue().set(QR_CODE_STATUS_PREFIX + sign, ScanCodeStatus.GENERATED.name());
            //生成二维码签名
            QrCodeDTO qrCodeDTO = new QrCodeDTO();
            qrCodeDTO.setSign(sign);
            qrCodeDTO.setImgUrl(qrCodeText);

            return qrCodeDTO;
        } catch (Exception e) {
            throw new BizException("生成二维码失败");
        }
    }

    /**
     * PC通过confirm Login生成密钥获取用户信息，并生成token返回给网站实现登陆
     *
     * @return
     */
    @Override
    public Object qrLogin(Map<String, Object> params) {
        Object secrit = params.get("secrit");
        Object sign = params.get("sign");
        Object qrCode = params.get("qrCode");
        Object userId=  redisTemplate.opsForValue().get(QR_CODE_SECRIT_PREFIX+secrit);
        //检查登陆二维码签名是否正确
        //密钥+qrCode +sign 验证sig算法是否正确 todo 只是临时测试
        User user = userService.getById(userId.toString());
        StpKit.USER.login(user.getId(), new SaLoginModel()
                .setExtra("email", user.getEmail())
                .setDevice("PC")// 此次登录的客户端设备类型, 用于[同端互斥登录]时指定此次登录的设备类型
                .setIsLastingCookie(true)// 是否为持久Cookie（临时Cookie在浏览器关闭时会自动删除，持久Cookie在重新打开后依然存在）
                .setTimeout(60 * 60 * 24 * 7)// 指定此次登录token的有效期, 单位:秒 （如未指定，自动取全局配置的 timeout 值）
                .setIsWriteHeader(true)// 是否在登录后将 Token 写入到响应头
        );
        return StpUtil.getTokenInfo();
    }

    @Override
    public Object checkStatus(Map<String, Object> params) {
        Object status = redisTemplate.opsForValue().get(QR_CODE_STATUS_PREFIX+params.get("sign"));

        if (ObjectUtils.isEmpty(status)) {
            return Map.of("status", ScanCodeStatus.EXPIRED.name());
        } else if (status.equals(ScanCodeStatus.CONFIRMED.name())) {
            Object userId = redisTemplate.opsForValue().get(QR_CODE_USER_PREFIX+params.get("sign"));
            if (userId == null) {
                throw new BizException("登陆失败");
            }
            //如果app确认了登陆，返回密钥
            String secrit = AlgUtils.guideRandom();
            redisTemplate.opsForValue().set(QR_CODE_SECRIT_PREFIX+secrit, userId);
            return Map.of("status", ScanCodeStatus.CONFIRMED.name(), "secrit", secrit);
        } else {
            return Map.of("status", status);
        }
    }

    @Override
    public Object confirmLogin(Map<String, Object> info) {
        Map m=(Map)info.get("info");
        String key = QR_CODE_STATUS_PREFIX + m.get("sign");
        //确认登陆并设置登陆状态为已登陆，并生成一个密钥存储到redis，前端通过轮训或者长链接checkstatus方法来获取登陆状态
        redisTemplate.opsForValue().set(key, ScanCodeStatus.CONFIRMED.name());
        return Map.of("status", ScanCodeStatus.CONFIRMED.name());
    }

    @Override
    public Object setStatus(String qrInfo) {
        Map<String, String> params = HttpUtils.parseToMap(qrInfo);
        String sign = params.get("sign");
        String key = QR_CODE_STATUS_PREFIX + params.get("sign");
        Object status = redisTemplate.opsForValue().get(key);
        if (status == null) {
            throw new BizException("二维码已失效");
        }
        //检查签名sign是否存在
        if (!StringUtils.hasText(status.toString())) {
            redisTemplate.opsForValue().set(key, ScanCodeStatus.EXPIRED.name());
            return Map.of("status", ScanCodeStatus.EXPIRED.name());
        }
        redisTemplate.opsForValue().set(key, ScanCodeStatus.SCANNED.name());
        //返回用户信息
        String userId = StpKit.USER.getLoginIdAsString();
        User currentUser = userService.getCurrentUser();
        Map<String, Object> json = Map.of("userId", userId, "nickname", currentUser.getNickname(), "sign", sign,"status",ScanCodeStatus.SCANNED.name());
        redisTemplate.opsForValue().set(QR_CODE_USER_PREFIX + sign, userId);
        return json;
    }

    @Override
    public Object qrCode(Map<String, Object> params) {
        return redisTemplate.opsForValue().get("sign");
    }
}
