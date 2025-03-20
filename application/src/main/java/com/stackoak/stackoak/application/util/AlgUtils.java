package com.stackoak.stackoak.application.util;

import java.util.Random;
import java.util.regex.Pattern;

public class AlgUtils {
    // 正则表达式验证格式和关键字符
    private static final String pattern = "^[0-9A-F]{7}-[0-9A-F]{4}-4[0-9A-F]{3}-[89AB][0-9A-F]{3}-[0-9A-F]{12}$";

    private static final Random random = new Random();

    public static String guideRandom() {
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

    public static boolean isValidGuid(String input) {
        if (input == null) {
            return false;
        }

        // 检查基本格式和长度
        if (!input.matches(pattern)) {
            return false;
        }

        // 分割字符串进行详细验证
        String[] parts = input.split("-");
        if (parts.length != 5) {
            return false;
        }

        // 检查各部分长度
        int[] expectedLengths = {7, 4, 4, 4, 12};
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].length() != expectedLengths[i]) {
                return false;
            }
        }

        // 检查第三个部分的第一个字符必须是 '4'
        if (parts[2].charAt(0) != '4') {
            return false;
        }

        // 检查第四部分的第一个字符必须是 8, 9, A, 或 B
        char yChar = parts[3].charAt(0);
        if (yChar != '8' && yChar != '9' && yChar != 'A' && yChar != 'B') {
            return false;
        }

        // 如果所有检查都通过，则返回 true
        return true;
    }

    public static void main(String[] args) {

    }
}
