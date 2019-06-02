package com.md.ecommerce.apiecommerce.controller;

import com.md.ecommerce.apiecommerce.domain.OrderRequest;
import com.md.ecommerce.apiecommerce.client.ShoppingServiceClient;
import com.md.ecommerce.commons.dto.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ecommerce/orders")
public class OrderController {

    private ShoppingServiceClient shoppingServiceClient;

    @Autowired
    public OrderController(ShoppingServiceClient shoppingServiceClient) {
        this.shoppingServiceClient = shoppingServiceClient;
    }

    @GetMapping("")
    public List<Order> getAllOrders() {
        return shoppingServiceClient.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public Order findOrderById(@PathVariable String orderId) {
        return shoppingServiceClient.findOrderById(orderId);
    }

    @PostMapping()
    public Order createOrderByProductsAndClient(@RequestBody OrderRequest orderRequest) {
        return shoppingServiceClient.createOrder(orderRequest);
    }
}