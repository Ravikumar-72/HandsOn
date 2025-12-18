package com.project.notification.service;

import com.project.notification.dto.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerEvent {

    // consumer without Retry-DLT logic
    /*@KafkaListener(topics = "order-demo", groupId = "order-g1")
    public void consumeEvent(Order order){
        log.info("Consumed the event {} ",order.toString());
    }
*/

    // Consumer with Retry-DLT(Dead Letter Topic) logic
    @KafkaListener(topics = "create-order",groupId = "order")
    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 2000,multiplier = 2),
            dltTopicSuffix = "-dlt",
            retryTopicSuffix = "-retry",
            autoCreateTopics = "true"
    )
    public void consumeRetryEvent(Order order){
        log.info("Received order: {}", order);
        if(order.getPrice() > 50000){
            throw new RuntimeException("Price too high, retry me!"); // simulated ERROR for Retry
        }
        log.info("Order processed successfully: {}", order);
    }

    @DltHandler
    public void handleDlt(Order order){
        log.error("Message moved to DLT: {}", order);
    }
}
