package com.stackoka.stackoka.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.baidu.fsg.uid","com.stackoka.stackoka"})
public class StackOkaApplication {
    public static void main(String[] args) {
        SpringApplication.run(StackOkaApplication.class, args);
    }
}
