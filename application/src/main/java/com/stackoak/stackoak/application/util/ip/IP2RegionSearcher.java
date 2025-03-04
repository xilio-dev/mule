package com.stackoak.stackoak.application.util.ip;

import org.lionsoul.ip2region.xdb.Searcher;

public class IP2RegionSearcher {
    private final Searcher searcher;

    // 私有构造函数，防止外部直接实例化
    private IP2RegionSearcher(Searcher searcher) {
        this.searcher = searcher;
    }

    // 静态工厂方法，用于创建 IP2RegionSearcher 实例
    public static IP2RegionSearcher create(String dbPath) {
        try {
            byte[] cBuff = Searcher.loadContentFromFile(dbPath);
            Searcher searcher = Searcher.newWithBuffer(cBuff);
            return new IP2RegionSearcher(searcher);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create IP2RegionSearcher", e);
        }
    }

    // 查询 IP 地址对应的区域信息，并根据国家返回不同的格式
    public String searchFormatted(String ip) {
        try {
            String region = searcher.search(ip);
            // 解析区域信息
            return formatRegion(region);
        } catch (Exception e) {
            throw new RuntimeException("Failed to search IP: " + ip, e);
        }
    }

    // 格式化区域信息
    private String formatRegion(String region) {
        if (region == null || region.isEmpty()) {
            return "Unknown";
        }

        // 区域信息格式：国家|0|省份|城市|运营商
        String[] parts = region.split("\\|");
        if (parts.length < 5) {
            return region; // 如果格式不符合预期，直接返回原始信息
        }

        String country = parts[0];
        String province = parts[2];

        if ("中国".equals(country)) {
            // 如果是中国，只返回省份
            return province;
        } else {
            // 如果是其他国家，只返回国家
            return country;
        }
    }

    // 关闭资源
    public void close() {
        try {
            searcher.close();
        } catch (Exception e) {
            System.err.println("Failed to close searcher: " + e.getMessage());
        }
    }

    // 示例用法
    public static void main(String[] args) {
        String dbPath = "application/src/main/resources/ip2region.xdb";
        IP2RegionSearcher searcher = IP2RegionSearcher.create(dbPath);
        try {
            String ip1 = "112.3.193.70"; // 中国 IP
            String ip2 = "8.8.8.8";       // 美国 IP
            String ip3 = "210.138.184.59"; // 日本 IP

            System.out.println(searcher.searchFormatted(ip1));
            System.out.println(searcher.searchFormatted(ip2));
            System.out.println(searcher.searchFormatted(ip3));
        } finally {
            searcher.close();
        }
    }
}
