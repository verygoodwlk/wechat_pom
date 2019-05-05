package com.qf.wechat_netty.mq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${netty.ip}")
    private String ip;

    @Value("${netty.port}")
    private String port;

    @Bean
    public Queue getQueue(){
        return new Queue("netty_queue_" + ip + ":" + port);
    }

    @Bean
    public Exchange getExChange(){
        return ExchangeBuilder.fanoutExchange("netty_exchange").build();
    }

    @Bean
    public Binding getBind(Queue getQueue, FanoutExchange getExChange){
        return BindingBuilder.bind(getQueue).to(getExChange);
    }
}
