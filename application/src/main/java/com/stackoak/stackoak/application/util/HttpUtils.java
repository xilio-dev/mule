package com.stackoak.stackoak.application.util;

import com.stackoak.stackoak.application.exception.BizException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {
    public static Map<String, String> parseToMap(String url) {
        try {
            // 分割基础URL和查询参数
            String[] urlParts = url.split("\\?");
            if (urlParts.length < 2) {
                return Collections.emptyMap();
            }

            String query = urlParts[1];
            Map<String, String> params = new HashMap<>();
            String[] pairs = query.split("&");

            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                // 处理无等号的参数（如"key"视为key=null）
                String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
                String value = idx > 0 && pair.length() > idx + 1 ?
                        URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;

                // 重复键时覆盖旧值
                params.put(key, value);
            }
            return Collections.unmodifiableMap(params);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
