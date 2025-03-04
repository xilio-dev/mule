package com.stackoak.stackoak.application.util;

import java.util.Random;

public class VerificationCodeGenerator {


    public static String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(9000) + 1000; // 生成 1000 到 9999 之间的随机数
        return String.valueOf(code);
    }

}
