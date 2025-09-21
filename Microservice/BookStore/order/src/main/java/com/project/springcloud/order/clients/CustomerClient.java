package com.project.springcloud.order.clients;

import com.project.springcloud.order.utils.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "customer-service",path = "/api/customers/")
public interface CustomerClient {

    @GetMapping("/{id}")
    CustomerDTO getCustomerDetails(@PathVariable("id") Long id);
}
