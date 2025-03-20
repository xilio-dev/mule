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
}
