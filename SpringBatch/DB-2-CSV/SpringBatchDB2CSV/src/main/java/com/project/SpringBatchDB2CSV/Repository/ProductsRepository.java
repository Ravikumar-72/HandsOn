package com.project.SpringBatchDB2CSV.Repository;

import com.project.SpringBatchDB2CSV.Entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, Long> {
}
