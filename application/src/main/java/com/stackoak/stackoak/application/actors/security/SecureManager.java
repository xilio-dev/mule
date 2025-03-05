package com.stackoak.stackoak.application.actors.security;

import cn.dev33.satoken.secure.SaSecureUtil;
import org.springframework.stereotype.Service;

@Service
public class SecureManager {
    // 定义私钥和公钥
    private static final String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAI/2ZN0lvH8JUezJnHbtb37qzlmhu5vf4jEoVvoJRquBRbnCZx0SAyWbM1puouS0DNU3dwnuUfD9jsWkr00re4TbuRkS4ULbX2FLFhMYJ6DJMHt7WdpYgHbmi7eIUhddDsBDqmJLJKTAQ5DR/KNOT/JDRmVk15UbNL4Nd/Lrb6QFAgMBAAECgYALmpYttyV27ebjGasZcPD8P2vrkJxBJ64hPTmphMbj+xMGMpPCJZoIVXaxstpBafvg0cwwCjwjmyR/bNNOepNFloJqZveMVFpivtB1YyI/Stjsdd4xJcadTvcOXs7e6kFK6tmVrqsKI7LoCO0Dck7EFlL4nh9kDiW8AC7KnSzwsQJBANsVOLxIeHM+kNXcWuEdoyWtpDwPC7QfbS+LRDUbFY8XFLZ+VTxlAcUEYxWGzmNYRgwccoUrdxkE7jEj7rxXjt0CQQCoOKJo3o+5Y2ufjAvtr3eveIDl8gAAingCohQzb7iMXTzn8O0JSS5MP6bmKM7Fl9H2g0qDNORvF0JCTVaku5NJAkBTmHpopcKIPvRnFkj7IyLwqpxDan5NwbwY6ywgk3AbhHQb2oLNo8EUG0NvoFu6XqGD14r/M4GjY1U8HPjolwRlAkEAg8+csr9xrroQYnvB/QQ4q+2oUoVFKydU0fteCM/Js3J7fuJxWY1WEbjudfoTyxAjHR3TDOws8j8vU/R1p66ywQJBAMI+ALYmdEMtzKn2m5uuq+dRZqTHIPOhG9Lci26BjmSVSfq8xe0TyB4GANOc2kfvgJZ+lpKjX0HN++WKaV84nbY=";
    private static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCP9mTdJbx/CVHsyZx27W9+6s5Zobub3+IxKFb6CUargUW5wmcdEgMlmzNabqLktAzVN3cJ7lHw/Y7FpK9NK3uE27kZEuFC219hSxYTGCegyTB7e1naWIB25ou3iFIXXQ7AQ6piSySkwEOQ0fyjTk/yQ0ZlZNeVGzS+DXfy62+kBQIDAQAB";

    public String encrypt(String plainText) {
        return SaSecureUtil.rsaEncryptByPublic(publicKey, plainText);
    }

    public String decrypt(String encryptedText) {
       return SaSecureUtil.rsaDecryptByPrivate(privateKey, encryptedText);
    }

    public static void main(String[] args) {
        SecureManager secureManager = new SecureManager();
        String encrypt = secureManager.encrypt("123456");
        String decrypt = secureManager.decrypt(encrypt);
        System.out.println(decrypt);

    }
}
