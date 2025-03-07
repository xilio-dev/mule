package com.stackoak.stackoak.application.service.common;

import com.stackoak.stackoak.common.data.material.UploadResultDTO;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadService {
    /**
     * 图片上传
     * @param file 上传文件
     * @return 上传结果
     */
    UploadResultDTO uploadImage(MultipartFile file);
}
