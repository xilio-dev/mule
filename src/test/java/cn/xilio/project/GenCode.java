package cn.xilio.project;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.nio.file.Paths;

public class GenCode {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/xilio1024?serverTimezone=UTC", "root", "root153212")
                .globalConfig(builder -> builder
                        .author("xilio.cn")
                        .dateType(DateType.TIME_PACK)
                        .outputDir(Paths.get(System.getProperty("user.dir")) + "/src/main/java")
                        .commentDate("yyyy-MM-dd")

                )
                .packageConfig(builder -> builder
                        .parent("cn.xilio.project")
                        .entity("bo")
                        .mapper("mapper")
                        .service("service")

                        .serviceImpl("service.impl")
                        .xml("mapper.xml")
                )
                .strategyConfig(builder -> builder
                        .entityBuilder()
                        .enableLombok()
                        .enableTableFieldAnnotation() // 启用字段注解
                        .controllerBuilder()
                        .enableRestStyle()


                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
