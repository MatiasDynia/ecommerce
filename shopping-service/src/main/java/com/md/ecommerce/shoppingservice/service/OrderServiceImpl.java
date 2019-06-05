package com.md.ecommerce.shoppingservice.service;

import com.md.ecommerce.commons.dto.Order;
import com.md.ecommerce.shoppingservice.entity.OrderEntity;
import com.md.ecommerce.shoppingservice.exception.OrderNotFoundException;
import com.md.ecommerce.shoppingservice.mapper.OrderMapper;
import com.md.ecommerce.shoppingservice.repository.OrderRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Override
    @HystrixCommand
    public Order findById(String id) {
        log.info("Retrieving order " + id + "...");

        Optional<OrderEntity> orderFound = orderRepository.findById(id);

        if(orderFound.isPresent()) {
            log.info("Order " + id + "found!");

            return OrderMapper.INSTANCE.map(orderFound.get());
        } else {
            throw new OrderNotFoundException("Order " + id + " not found!!");
        }
    }

    @Override
    @HystrixCommand
    public Iterable<Order> findAllOrders() {
        log.info("Retrieving all orders...");

        return OrderMapper.INSTANCE.map(orderRepository.findAll());
    }

    @Override
    @HystrixCommand
    public Order saveOrder(OrderEntity orderEntity) {
        orderEntity.setId(sequenceGeneratorService.generateSequence(OrderEntity.SEQUENCE_NAME));

        log.info("Saving new order...");

        Order orderSaved = OrderMapper.INSTANCE.map(orderRepository.save(orderEntity));

        log.info("Order " + orderSaved.getId() + " successfully saved!");

        return orderSaved;
    }
}
