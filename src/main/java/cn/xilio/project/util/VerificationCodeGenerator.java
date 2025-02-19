package cn.xilio.project.util;

import java.util.Random;

public class VerificationCodeGenerator {

    /**
     * 生成 4 位随机数字验证码
     *
     * @return 4 位数字验证码
     */
    public static String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(9000) + 1000; // 生成 1000 到 9999 之间的随机数
        return String.valueOf(code);
    }

}
