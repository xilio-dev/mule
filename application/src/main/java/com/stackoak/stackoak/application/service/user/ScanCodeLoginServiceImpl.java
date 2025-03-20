package com.stackoak.stackoak.application.service.user;

import com.stackoak.stackoak.application.config.StackOakConfig;
import com.stackoak.stackoak.application.exception.BizException;
import com.stackoak.stackoak.common.data.Constants;
import com.stackoak.stackoak.common.data.auth.QrCodeDTO;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
    private static final Random random = new Random();

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
            //File file = QRCode.from(qrCodeText).file();
            String absPath = getAbsoluteFile(StackOakConfig.getUploadPath(), sign + ".png").getAbsolutePath();

            String imgUrl = getPathFileName(StackOakConfig.getUploadPath(), sign + ".png");
            String qrCodeText = server + imgUrl + "?sign=" + sign;
            QRCode.from(qrCodeText).to(ImageType.PNG).writeTo(new FileOutputStream(absPath));

            //将二维码存储到redis，并设置过期时间
            redisTemplate.opsForValue().set(sign, qrCodeText, 60 * 60 * 24, TimeUnit.SECONDS);
            //生成二维码签名
            QrCodeDTO qrCodeDTO = new QrCodeDTO();
            qrCodeDTO.setSign(sign);
            qrCodeDTO.setImgUrl(qrCodeText);

            return qrCodeDTO;
        } catch (Exception e) {
            throw new BizException("生成二维码失败");
        }
    }

    public File getAbsoluteFile(String uploadDir, String fileName) {
        File desc = new File(uploadDir + File.separator + fileName);
        if (!desc.exists()) {
            if (!desc.getParentFile().exists()) {
                desc.getParentFile().mkdirs();
            }
        }
        return desc;
    }

    public static final String getPathFileName(String uploadDir, String fileName) throws IOException {
        int dirLastIndex = StackOakConfig.getProfile().length() + 1;
        String currentDir = StringUtils.substring(uploadDir, dirLastIndex);
        return Constants.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
    }

    private String guideRandom() {
        String template = "xxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx";
        StringBuilder gid = new StringBuilder();
        for (char z : template.toCharArray()) {
            // 生成 0 到 15 之间的随机数
            int t = random.nextInt(16); // 生成 [0, 16) 范围内的随机整数

            if (z == 'x') {
                // 当 z 是 'x' 时，使用随机数 t
                gid.append(Integer.toHexString(t));
            } else if (z == 'y') {
                // 当 z 是 'y' 时，生成 8、9、A、B 某个随机数
                gid.append(Integer.toHexString((t & 0x0f) | 0x08));
            } else {
                // 不是 'x' 或 'y'，直接添加原字符
                gid.append(z);
            }
        }

        // 将字符串转换为大写
        return gid.toString().toUpperCase();
    }

    /**
     * PC通过confirm Login生成密钥获取用户信息，并生成token返回给网站实现登陆
     *
     * @return
     */
    @Override
    public Object qrLogin(Map<String, Object> params) {
        //检查登陆二维码签名是否正确
        //密钥+qrCode +sign 验证sig算法是否正确
        HashMap<Object, Object> data = new HashMap<>();
        data.put("token", "dsdsd77458478");
        return data;
    }

    @Override
    public Object checkStatus(Map<String, Object> params) {
        return redisTemplate.opsForValue().get(params.get("sign") + ":status");
    }

    @Override
    public Object confirmLogin(Map<String, Object> params) {
        //确认登陆并设置登陆状态为已登陆，并生成一个密钥存储到redis，前端通过轮训或者长链接checkstatus方法来获取登陆状态
        HashMap<Object, Object> data = new HashMap<>();
        data.put("status", "confirm");
        redisTemplate.opsForValue().set(params.get("sign") + ":status",  "confirm", 60 * 60 * 24, TimeUnit.SECONDS);
        return data;
    }

    @Override
    public Object setStatus(Map<String, Object> params) {
        HashMap<Object, Object> data = new HashMap<>();
        data.put("status", params.get("status"));
        redisTemplate.opsForValue().set(params.get("sign") + ":status", params.get("status"), 60 * 60 * 24, TimeUnit.SECONDS);
        return data;
    }

    @Override
    public Object qrCode(Map<String, Object> params) {
        return redisTemplate.opsForValue().get("sign");
    }
}
