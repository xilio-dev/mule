package com.stackoak.stackoak.application.util;

import com.stackoak.stackoak.application.config.StackOakConfig;
import com.stackoak.stackoak.common.data.Constants;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;

public class FileUtils {
    public static File getAbsoluteFile(String uploadDir, String fileName) {
        File desc = new File(uploadDir + File.separator + fileName);
        if (!desc.exists()) {
            if (!desc.getParentFile().exists()) {
                desc.getParentFile().mkdirs();
            }
        }
        return desc;
    }

    public static String getPathFileName(String uploadDir, String fileName) throws IOException {
        int dirLastIndex = StackOakConfig.getProfile().length() + 1;
        String currentDir = StringUtils.substring(uploadDir, dirLastIndex);
        return Constants.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
    }
    /**
     * 从文件名中获取文件后缀名（含点号）
     * 示例: "example.txt" -> ".txt", "myfile.tar.gz" -> ".gz", "noextension" -> ""
     *
     * @param fileName 文件名，可以包含路径
     * @return 文件后缀名（小写，含点号），如果没有后缀名则返回空字符串
     */
    public static String getFileExtensionWithDot(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            return "";
        }

        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == 0 || lastDotIndex == fileName.length() - 1) {
            return ""; // 没有后缀名、文件名以点开头或结尾
        }

        return fileName.substring(lastDotIndex).toLowerCase();
    }
}
