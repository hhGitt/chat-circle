package com.hh.chat.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/***
* @Description: Swagger页面相关信息
* @Param:
* @return:
* @Author: hh
* @Date: 2023/5/14
*/

@Slf4j
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    //配置Swagger的Docket的bean实例
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo());//配置Swagger信息
    }

    //配置Swagger信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("HH的项目")
                .description("ChatService API文档")
                .contact(new Contact("HH", "https://gist.github.com/hhGitt", "2421159018@qq.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                .version("1.0").build();
    }

}
