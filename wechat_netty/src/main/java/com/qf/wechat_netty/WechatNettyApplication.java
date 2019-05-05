package com.qf.wechat_netty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.qf")
public class WechatNettyApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatNettyApplication.class, args);
    }

}
