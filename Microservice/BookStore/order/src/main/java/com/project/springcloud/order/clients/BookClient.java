package com.project.springcloud.order.clients;

import com.project.springcloud.order.utils.BookDTO;
import com.project.springcloud.order.utils.StockUpdate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "book-service", path = "/api/books/")
public interface BookClient {

    @PutMapping("stock/{id}")
    BookDTO reserveBook(@PathVariable("id") Long id, @RequestBody StockUpdate stockUpdate);
}
