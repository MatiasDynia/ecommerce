package com.md.ecommerce.shoppingservice.repository;

import com.md.ecommerce.shoppingservice.TestUtils;
import com.md.ecommerce.shoppingservice.entity.OrderEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@DataMongoTest
@RunWith(SpringRunner.class)
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void shouldFindOrderById() {

        OrderEntity order = TestUtils.createTestOrder();

        orderRepository.save(order);

        OrderEntity ordersFound = orderRepository.findById(order.getId()).get();

        assertEquals(order.getId(), ordersFound.getId());
        assertEquals(order.getDate(), ordersFound.getDate());
        assertEquals(order.getOrderState(), ordersFound.getOrderState());
        assertEquals(order.getProducts().get(0).getProduct().getCode(),
                ordersFound.getProducts().get(0).getProduct().getCode());
        assertEquals(order.getProducts().get(1).getProduct().getCode(),
                ordersFound.getProducts().get(1).getProduct().getCode());
        assertEquals(order.getClient().getId(), ordersFound.getClient().getId());
    }
}