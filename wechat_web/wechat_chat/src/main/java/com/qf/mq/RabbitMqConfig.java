package com.qf.mq;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public Exchange getExchange(){
        return ExchangeBuilder.fanoutExchange("netty_exchange").build();
    }
}
