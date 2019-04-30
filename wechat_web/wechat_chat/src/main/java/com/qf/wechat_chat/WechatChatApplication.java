package com.qf.wechat_chat;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.qf")
@EnableEurekaClient
public class WechatChatApplication {


    @Value("${zk.host}")
    private String host;

    public static void main(String[] args) {
        SpringApplication.run(WechatChatApplication.class, args);
    }

    @Bean
    public ZkClient getZkClient(){
        ZkClient zkClient = new ZkClient(host, 10000);
        return zkClient;
    }

}
