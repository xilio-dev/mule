package com.stackoak.stackoak.common.data.collect;

import java.util.List;

public record SaveArticleToCollectRequest(String aid, List<String> cIds) {
}
