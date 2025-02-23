package cn.xilio.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"cn.xilio.project","com.baidu.fsg.uid"})
public class Setup {

    public static void main(String[] args) {
        SpringApplication.run(Setup.class, args);
    }

}
