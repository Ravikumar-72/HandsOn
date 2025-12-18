package com.project.order.service;

import com.project.order.dto.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class OrderService {


    @Autowired
    private KafkaTemplate<String, Object> template;

    public void createOrderEvent(Order order) {
        CompletableFuture<SendResult<String, Object>> sent = template.send("create-order", order);
        sent.whenComplete((result, exp) -> {
            if(exp == null){
                log.info("Sent message ["+ order.toString() +"] and the partition ["+result.getRecordMetadata().partition()+"]");
            }else{
                log.error("Unable to send message=[" + order.toString() +"] due to: "+exp.getMessage());
            }
        });
    }
}
