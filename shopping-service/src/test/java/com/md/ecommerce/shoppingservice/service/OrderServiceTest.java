package com.md.ecommerce.shoppingservice.service;

import com.md.ecommerce.commons.dto.Order;
import com.md.ecommerce.shoppingservice.TestUtils;
import com.md.ecommerce.shoppingservice.entity.OrderEntity;
import com.md.ecommerce.shoppingservice.repository.OrderRepository;
import org.assertj.core.util.IterableUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private SequenceGeneratorService sequenceGeneratorService;

    @Test
    public void shouldFindOrderById() {

        Optional<OrderEntity> order = Optional.of(TestUtils.createTestOrder());

        when(orderRepository.findById(order.get().getId())).thenReturn(order);

        Order orderFound = orderService.findById(order.get().getId());

        verify(orderRepository).findById(order.get().getId());
        assertEquals(order.get().getId(), orderFound.getId());
    }

    @Test
    public void shouldFindAllOrders() {
        List<OrderEntity> orderEntities = new ArrayList<>();
        orderEntities.add(TestUtils.createTestOrder());
        orderEntities.add(TestUtils.createTestOrder());
        orderEntities.add(TestUtils.createTestOrder());

        when(orderRepository.findAll()).thenReturn(orderEntities);

        Iterable<Order> foundOrders = orderService.findAllOrders();

        verify(orderRepository).findAll();
        assertEquals(3, IterableUtil.sizeOf(foundOrders));
    }

    @Test
    public void shouldSaveOrder() {
        OrderEntity order = TestUtils.createTestOrder();

        when(orderRepository.save(order)).thenReturn(order);
        when(sequenceGeneratorService.generateSequence(OrderEntity.SEQUENCE_NAME)).thenReturn("1");

        Order orderSaved = orderService.saveOrder(order);

        verify(orderRepository).save(order);
        assertEquals(order.getId(), orderSaved.getId());
    }
}