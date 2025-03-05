package com.stackoak.stackoak.application.controller.common;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "stackoak.fileUploadModel",havingValue = "Minio")
public class MinioUploadService implements IUploadService{
}
