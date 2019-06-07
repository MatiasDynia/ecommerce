package com.md.ecommerce.shoppingservice.controller;

import com.md.ecommerce.commons.dto.Order;
import com.md.ecommerce.shoppingservice.entity.OrderEntity;
import com.md.ecommerce.shoppingservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shopping/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("")
    public Iterable<Order> getAllOrders() {
        return orderService.findAllOrders();
    }

    @GetMapping("/{id}")
    public Order findOrderById(@PathVariable("id") String id) {
        return orderService.findById(id);
    }

    @PostMapping()
    public Order saveOrder(@RequestBody OrderEntity order) {
        return orderService.saveOrder(order);
    }

    @PutMapping
    public Order updateOrderState(@RequestParam("id") String id, @RequestParam("state") String state) {
        return orderService.updateOrderStatus(id, state);
    }

}
