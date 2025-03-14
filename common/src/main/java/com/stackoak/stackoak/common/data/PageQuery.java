package com.stackoak.stackoak.common.data;

import lombok.*;

import java.io.Serializable;

@ToString
@Setter
@Getter
@NoArgsConstructor
public class PageQuery implements Serializable {
    private static final long serialVersionUID = 1L;
    @NonNull
    private Long current = 1L;
    @NonNull
    private Long size = 5L;

    public @NonNull Long getCurrent() {
        if (current < 1) {
            current = 1L;
        }
        return current;
    }

    public @NonNull Long getSize() {
        if (size > 1000) {
            throw new IllegalArgumentException("size must less than 1000");
        }
        return size;
    }
}
