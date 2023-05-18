package com.hh.gateway;

import com.hh.gateway.config.LoadBalancerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@LoadBalancerClients({
        @LoadBalancerClient(value = "snowflake-service", configuration = LoadBalancerConfig.class),
        @LoadBalancerClient(value = "user-service", configuration = LoadBalancerConfig.class),
        @LoadBalancerClient(value = "chat-service", configuration = LoadBalancerConfig.class),
})  // 负载均衡策略
@ComponentScan(basePackages = {"com.hh.commons","com.hh.gateway"})
@EnableSwagger2 // Swagger的开关，表示已经启用Swagger
@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
