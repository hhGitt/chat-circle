package com.hh.snowflake.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

/***
* @Description: Swagger页面相关信息
* @Param:
* @return:
* @Author: hh
* @Date: 2023/5/14
*/

@Configuration
@EnableOpenApi //开启Swagger3
public class SwaggerConfig {
    //配置Swagger的Docket的bean实例
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo());//配置Swagger信息
    }

    //配置Swagger信息
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "HH的项目",
                "IdService API文档",
                "1.0",//版本信息
                "https://gist.github.com/hhGitt",// 团队信息的url
                new Contact("HH", "https://gist.github.com/hhGitt", "2421159018@qq.com"),// 作者信息
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
    }
}
