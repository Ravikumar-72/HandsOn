package com.project.springcloud.order.service;

import com.project.springcloud.order.clients.BookClient;
import com.project.springcloud.order.clients.CustomerClient;
import com.project.springcloud.order.entity.Order;
import com.project.springcloud.order.entity.OrderStatus;
import com.project.springcloud.order.repository.OrderRepository;
import com.project.springcloud.order.utils.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.logging.Logger;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookClient bookClient;

    @Autowired
    private CustomerClient customerClient;

    public Order newOrder(CreateOrderDTO createOrderDTO) {
        CustomerDTO customer = customerClient.getCustomerDetails(createOrderDTO.getCustomerId());
        System.out.println("CUSTOMER :: "+customer.toString());

        StockUpdate stockUpdate = new StockUpdate();
        stockUpdate.setStockQuantity(createOrderDTO.getQuantity());
        BookDTO book = bookClient.reserveBook(createOrderDTO.getBookId(), stockUpdate);
        System.out.println("BOOK :: "+book.toString());

        Order order = new Order();
        order.setCustomerId(createOrderDTO.getCustomerId());
        order.setBookId(createOrderDTO.getBookId());
        order.setQuantity(createOrderDTO.getQuantity());
        order.setTotalPrice(createOrderDTO.getQuantity() * book.getPrice());
        order.setOrderDate(LocalDate.now());
        order.setStatus(OrderStatus.CREATED);

        orderRepository.save(order);
        System.out.println("ORDER :: "+order.toString());

        return order;
    }
}
