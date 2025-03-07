package com.stackoak.stackoak.application.controller.common;

import com.stackoak.stackoak.application.service.common.IUploadService;
import com.stackoak.stackoak.common.message.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("file")
public class FileUploadController {
    @Autowired
    private IUploadService uploadService;
    @PostMapping("upload")
    public RestResult uploadFile(@RequestParam("file") MultipartFile file) {
        return RestResult.success(uploadService.uploadImage(file));
    }
}
