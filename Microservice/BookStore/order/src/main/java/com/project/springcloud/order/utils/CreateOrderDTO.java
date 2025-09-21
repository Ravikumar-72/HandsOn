package com.project.springcloud.order.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateOrderDTO {

    private Long customerId;
    private Long bookId;
    private Integer quantity;
}
