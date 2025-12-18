package com.project.order.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.order.dto.Order;
import com.project.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/create")
    public ResponseEntity<String> createOrder(){
        orderService.createOrderEvent(new Order("104","Samsung S23",65000));
        return ResponseEntity.status(HttpStatus.CREATED).body("Order sent to topic successfully");
    }

    /*@PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody Order order){
        orderService.createOrderEvent(order);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order sent to topic successfully");
    }*/
}
