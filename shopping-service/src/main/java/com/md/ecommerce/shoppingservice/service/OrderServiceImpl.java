package com.md.ecommerce.shoppingservice.service;

import com.md.ecommerce.shoppingservice.entity.OrderEntity;
import com.md.ecommerce.shoppingservice.exception.OrderNotFoundException;
import com.md.ecommerce.shoppingservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
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
    public OrderEntity findById(String id) {
        Optional<OrderEntity> orderFound = orderRepository.findById(id);

        if(orderFound.isPresent()) {
            return orderFound.get();
        } else {
            throw new OrderNotFoundException("Order " + id + " not found!!");
        }
    }

    @Override
    public Iterable<OrderEntity> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public OrderEntity saveOrder(OrderEntity orderEntity) {
        orderEntity.setId(sequenceGeneratorService.generateSequence(OrderEntity.SEQUENCE_NAME));

        return orderRepository.save(orderEntity);
    }
}
