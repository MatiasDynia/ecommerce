package com.md.ecommerce.shoppingservice.controller;

import com.md.ecommerce.commons.dto.Order;
import com.md.ecommerce.shoppingservice.entity.OrderEntity;
import com.md.ecommerce.shoppingservice.mapper.OrderMapper;
import com.md.ecommerce.shoppingservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api/shopping/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("")
    public Iterable<OrderEntity> getAllOrders() {
        return orderService.findAllOrders();
    }

    @GetMapping("/{id}")
    public Order findOrderById(@PathVariable("id") String id) {
        return OrderMapper.INSTANCE.map(orderService.findById(id));
    }

    @PostMapping("")
    public OrderEntity saveOrder(@RequestBody OrderEntity order) {
        return orderService.saveOrder(order);
    }

}
