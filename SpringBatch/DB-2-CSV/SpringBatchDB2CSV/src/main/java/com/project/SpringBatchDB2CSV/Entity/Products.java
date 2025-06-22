package com.project.SpringBatchDB2CSV.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="products", schema="coffee_store")
@Data
public class Products {

    @Id
    private Long id;
    private String name;
    private Double price;
    private String coffee_origin;


}
