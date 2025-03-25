package com.stackoak.stackoak.application.service.common;

import com.stackoak.stackoak.common.data.material.UploadResultDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@ConditionalOnProperty(name = "stackoak.fileUploadModel",havingValue = "Minio")
public class MinioUploadService extends AbstractUploadService{

    /**
     * 图片上传
     *
     * @param file 上传文件
     * @return 上传结果
     */
    @Override
    public UploadResultDTO uploadImage(MultipartFile file) {

        return null;
    }
}
