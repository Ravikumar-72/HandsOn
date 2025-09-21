package com.project.springcloud.order.controller;

import com.project.springcloud.order.entity.Order;
import com.project.springcloud.order.service.OrderService;
import com.project.springcloud.order.utils.CreateOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/")
    public ResponseEntity<Order> order(@RequestBody CreateOrderDTO createOrderDTO){
        Order confirmedOrder = orderService.newOrder(createOrderDTO);
        return ResponseEntity.status(HttpStatus.OK).body(confirmedOrder);
    }
}
