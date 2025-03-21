package com.stackoak.stackoak.application.util;

public class StringTools {
    /**
     * 检查字符串是否包含特殊字符
     * @param str 输入字符串
     * @return boolean 是否包含特殊字符
     */
    public static boolean hasSpecialChars(String str) {
        if (str == null) {
            return false; // 处理空指针情况
        }
        // 定义特殊字符的正则表达式，与 TypeScript 版本保持一致
        String specialChars = "[!@#$%^&*()_+-=\\[\\]{};:'\"\\\\|,.<>/?]+";
        return str.matches(".*" + specialChars + ".*");
    }
}
