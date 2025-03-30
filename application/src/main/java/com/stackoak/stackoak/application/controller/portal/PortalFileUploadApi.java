package com.stackoak.stackoak.application.controller.portal;

import com.stackoak.stackoak.application.service.common.IUploadService;
import com.stackoak.stackoak.common.message.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("file")
public class PortalFileUploadApi {
    @Autowired
    private IUploadService uploadService;
    @PostMapping("upload")
    public Result uploadFile(@RequestParam("file") MultipartFile file) {
        return Result.success(uploadService.uploadImage(file));
    }
}
