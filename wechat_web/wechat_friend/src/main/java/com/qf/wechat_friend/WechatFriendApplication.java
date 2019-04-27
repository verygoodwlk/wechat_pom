package com.qf.wechat_friend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.qf")
@EnableEurekaClient
@MapperScan("com.qf.dao")
@EnableFeignClients("com.qf")
@EnableTransactionManagement
public class WechatFriendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatFriendApplication.class, args);
    }

}
