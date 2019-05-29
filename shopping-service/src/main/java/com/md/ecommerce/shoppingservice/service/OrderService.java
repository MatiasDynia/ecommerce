package com.md.ecommerce.shoppingservice.service;

import com.md.ecommerce.shoppingservice.entity.OrderEntity;

import java.util.Optional;

public interface OrderService {

    OrderEntity findById(String id);
    Iterable<OrderEntity> findAllOrders();
    OrderEntity saveOrder(OrderEntity orderEntity);
}
