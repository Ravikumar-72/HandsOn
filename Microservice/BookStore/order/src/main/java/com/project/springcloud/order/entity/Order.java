package com.project.springcloud.order.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "orders")
@ToString
public class Order {

    @Id
    private String orderId;

    private Long customerId;
    private Long bookId;
    private Integer quantity;
    private Double totalPrice;
    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @PrePersist
    public void autoGenerateOrderId(){
        if(this.orderId == null){
            this.orderId = "ORD-"+ UUID.randomUUID().toString().substring(0,8).toUpperCase();
        }
    }
}
