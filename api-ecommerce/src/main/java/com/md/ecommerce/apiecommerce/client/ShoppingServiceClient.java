package com.md.ecommerce.apiecommerce.client;

import com.md.ecommerce.commons.dto.Order;

import java.util.List;

public interface ShoppingServiceClient {

    public List<Order> getAllOrders();
    public Order findOrderById(String id);
    public Order saveOrder(Order order);
}
