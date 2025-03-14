package com.stackoak.stackoak.common.data;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class PageQuery implements Serializable {
    private static final long serialVersionUID = 1L;
    @NonNull
    private Long current=1L;
    @NonNull
    private Long size=5L;
}
