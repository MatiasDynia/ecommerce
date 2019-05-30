package com.md.ecommerce.shoppingservice.service;

import com.md.ecommerce.commons.dto.Order;
import com.md.ecommerce.shoppingservice.entity.OrderEntity;

public interface OrderService {

    Order findById(String id);
    Iterable<Order> findAllOrders();
    Order saveOrder(OrderEntity orderEntity);
}
