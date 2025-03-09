package com.stackoak.stackoak.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.baidu.fsg.uid","com.stackoak.stackoak"})
public class StackOakApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(StackOakApplication.class, args);

    }

}
