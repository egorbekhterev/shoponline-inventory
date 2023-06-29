package com.shoponline.inventoryservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue inventoryQueue() {
        return new Queue("inventoryQueue", true);
    }
}
