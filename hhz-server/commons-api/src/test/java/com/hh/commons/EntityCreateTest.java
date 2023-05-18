package com.hh.commons;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;


public class EntityCreateTest {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/hh_server", "root", "chenhang")
                .globalConfig(builder -> {
                    builder.author("hh") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D://test/"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("generator") // 设置父包名
                            .moduleName("system") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D://test/mapperXml")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(List.of("chat_msg","friends_request","my_friends")); // 设置需要生成的表名
                    builder.entityBuilder().enableLombok();
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
