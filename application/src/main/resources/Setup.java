package com.stackoka.stackoka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.stackoak","com.baidu.fsg.uid"})
public class Setup {

    public static void main(String[] args) {
        SpringApplication.run(Setup.class, args);
    }

}
