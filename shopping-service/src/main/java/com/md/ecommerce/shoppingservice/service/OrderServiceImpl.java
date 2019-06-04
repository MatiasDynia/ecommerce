package com.md.ecommerce.shoppingservice.service;

import com.md.ecommerce.commons.dto.Order;
import com.md.ecommerce.shoppingservice.entity.OrderEntity;
import com.md.ecommerce.shoppingservice.exception.OrderNotFoundException;
import com.md.ecommerce.shoppingservice.mapper.OrderMapper;
import com.md.ecommerce.shoppingservice.repository.OrderRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Override
    @HystrixCommand
    public Order findById(String id) {
        Optional<OrderEntity> orderFound = orderRepository.findById(id);

        if(orderFound.isPresent()) {
            return OrderMapper.INSTANCE.map(orderFound.get());
        } else {
            throw new OrderNotFoundException("Order " + id + " not found!!");
        }
    }

    @Override
    @HystrixCommand
    public Iterable<Order> findAllOrders() {
        return OrderMapper.INSTANCE.map(orderRepository.findAll());
    }

    @Override
    @HystrixCommand
    public Order saveOrder(OrderEntity orderEntity) {
        orderEntity.setId(sequenceGeneratorService.generateSequence(OrderEntity.SEQUENCE_NAME));

        return OrderMapper.INSTANCE.map(orderRepository.save(orderEntity));
    }
}
