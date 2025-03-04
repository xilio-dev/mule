package com.stackoak.stackoak.application.util.ip;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

    // 第三方 API 地址，用于获取公网 IP
    private static final String[] IP_API_URLS = {
            "https://api.ipify.org",
            "https://checkip.amazonaws.com",
            "https://ipinfo.io/ip"
    };

    /**
     * 获取当前网络的公网 IP 地址
     *
     * @return 公网 IP 地址，如果获取失败则返回 null
     */
    public static String getPublicIpAddress() {
        for (String apiUrl : IP_API_URLS) {
            try {
                // 创建 URL 对象
                URL url = new URL(apiUrl);
                // 打开连接
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // 读取响应
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String ipAddress = reader.readLine();
                reader.close();

                // 验证 IP 地址是否有效
                if (ipAddress != null && !ipAddress.isEmpty()) {
                    return ipAddress;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String publicIp = getPublicIpAddress();
        System.out.println(publicIp);
    }
}
