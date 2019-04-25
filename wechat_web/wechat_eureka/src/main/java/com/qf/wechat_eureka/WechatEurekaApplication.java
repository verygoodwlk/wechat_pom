package com.qf.wechat_eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class WechatEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatEurekaApplication.class, args);
    }

}
