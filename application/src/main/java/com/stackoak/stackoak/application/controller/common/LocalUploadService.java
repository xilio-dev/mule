package com.stackoak.stackoak.application.controller.common;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
@ConditionalOnProperty(name = "stackoak.fileUploadModel",havingValue = "Local")
public class LocalUploadService implements IUploadService{
}
