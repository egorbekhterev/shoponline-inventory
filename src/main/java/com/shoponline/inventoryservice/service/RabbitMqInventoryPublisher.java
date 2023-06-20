package com.shoponline.inventoryservice.service;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RabbitMqInventoryPublisher {

    private RabbitTemplate rabbitTemplate;

    public void publishInventoryUpdate(String message) {
        rabbitTemplate.convertAndSend("inventoryQueue", message);
    }
}
