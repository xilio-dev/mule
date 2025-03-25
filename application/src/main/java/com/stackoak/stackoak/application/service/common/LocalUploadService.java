package com.stackoak.stackoak.application.service.common;

import com.stackoak.stackoak.application.config.StackOakConfig;
import com.stackoak.stackoak.application.exception.BizException;
import com.stackoak.stackoak.application.util.FileUtils;
import com.stackoak.stackoak.common.data.Constants;
import com.stackoak.stackoak.common.data.material.Material;
import com.stackoak.stackoak.common.data.material.UploadResultDTO;
import com.stackoak.stackoak.common.message.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

@Primary
@Component
@ConditionalOnProperty(name = "stackoak.fileUploadModel", havingValue = "Local")
public class LocalUploadService extends AbstractUploadService {

    /**
     * 图片上传
     *
     * @param file 上传文件
     * @return 上传结果
     */
    @Override
    public UploadResultDTO uploadImage(MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                //todo 图片格式检查
                String originFileName = file.getOriginalFilename();
                String suffix = FileUtils.getFileExtensionWithDot(originFileName);
                ThreadLocalRandom random = ThreadLocalRandom.current();
                // 生成 10 位随机整数 (1000000000 到 9999999999)
                long min = 1_000_000_000L; // 10 位数的最小值
                long max = 9_999_999_999L; // 10 位数的最大值
                String key = String.valueOf(min + random.nextLong(max - min + 1));
                String fileName = key + suffix;
                File absolutePath = FileUtils.getAbsoluteFile(StackOakConfig.getUploadPath(), fileName);
                file.transferTo(absolutePath);/*将图片写到文件系统*/
                int dirLastIndex = StackOakConfig.getProfile().length() + 1;
                String currentDir = StringUtils.substring(StackOakConfig.getUploadPath(), dirLastIndex);
                String imgUrl = Constants.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
                return new UploadResultDTO(
                        key,
                        imgUrl,
                        originFileName,
                        fileName,
                        file.getSize(),
                        file.getContentType());}
            throw new BizException("上传图片不能为空！");
        } catch (Exception e) {
            throw new BizException("上传失败，请重试！");
        }
    }
}
