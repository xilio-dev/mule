package com.stackoak.stackoak.common.data.material;

import java.io.Serializable;
public record UploadResultDTO (String key,String imgUrl,String originName,String fileName,Long size,String contentType) implements Serializable {

}
