package com.stackoak.stackoak.application.service.common;

import com.stackoak.stackoak.common.data.material.UploadResultDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
        UploadResultDTO dto = new UploadResultDTO();
        dto.setImgUrl("http://stackoak.com/" + file.getOriginalFilename());
        return dto;
    }
}
