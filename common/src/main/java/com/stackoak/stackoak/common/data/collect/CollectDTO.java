package com.stackoak.stackoak.common.data.collect;

public record CollectDTO(
        String id,
        String name,
        String description,
        String userId,
        Boolean isCollect // 当前文章是否在此收藏夹中
) {
}
