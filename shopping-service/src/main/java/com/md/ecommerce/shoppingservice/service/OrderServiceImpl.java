package com.md.ecommerce.shoppingservice.service;

import com.md.ecommerce.commons.dto.Order;
import com.md.ecommerce.shoppingservice.entity.OrderEntity;
import com.md.ecommerce.shoppingservice.exception.OrderNotFoundException;
import com.md.ecommerce.shoppingservice.mapper.OrderMapper;
import com.md.ecommerce.shoppingservice.repository.OrderRepository;
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
    public Order findById(String id) {
        Optional<OrderEntity> orderFound = orderRepository.findById(id);

        if(orderFound.isPresent()) {
            return OrderMapper.INSTANCE.map(orderFound.get());
        } else {
            throw new OrderNotFoundException("Order " + id + " not found!!");
        }
    }

    @Override
    public Iterable<Order> findAllOrders() {
        return OrderMapper.INSTANCE.map(orderRepository.findAll());
    }

    @Override
    public Order saveOrder(OrderEntity orderEntity) {
        orderEntity.setId(sequenceGeneratorService.generateSequence(OrderEntity.SEQUENCE_NAME));

        return OrderMapper.INSTANCE.map(orderRepository.save(orderEntity));
    }
}
