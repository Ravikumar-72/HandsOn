package com.project.springcloud.order.utils;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BookDTO {

    private String title;
    private String author;
    private String description;
    private String genre;
    private Double price;
    private Integer stockQuantity;
}
