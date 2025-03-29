package com.stackoak.stackoak.common.data.column;

import jakarta.validation.constraints.NotEmpty;

public record SubscribeRequest(@NotEmpty String columnId) {
}
