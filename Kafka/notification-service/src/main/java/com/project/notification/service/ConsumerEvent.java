package com.project.notification.service;

import com.project.notification.dto.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerEvent {

    @KafkaListener(topics = "order-demo", groupId = "order-g1")
    public void consumeEvent(Order order){
        log.info("Consumed the event {} ",order.toString());
    }
}
