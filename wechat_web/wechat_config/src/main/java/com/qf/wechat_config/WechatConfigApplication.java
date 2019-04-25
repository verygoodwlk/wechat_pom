package com.qf.wechat_config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class WechatConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatConfigApplication.class, args);
    }

}
