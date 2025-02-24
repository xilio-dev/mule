package cn.xilio.project;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.ibatis.type.JdbcType;

import java.nio.file.Paths;

public class GenCode {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://124.221.53.234:3306/xilio1024?serverTimezone=UTC", "xilio1024", "EbfSKppBwZMemEGW")
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            // 兼容旧版本转换成Integer
                            if (JdbcType.TINYINT == metaInfo.getJdbcType()) {
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .globalConfig(builder -> builder
                        .author("stackoak.com")
                        .dateType(DateType.TIME_PACK)
                        .outputDir(Paths.get(System.getProperty("user.dir")) + "/gen")
                        .commentDate("yyyy-MM-dd HH:mm:ss")

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
