package com.md.ecommerce.shoppingservice.mapper;

import com.md.ecommerce.commons.dto.Order;
import com.md.ecommerce.shoppingservice.TestUtils;
import com.md.ecommerce.shoppingservice.entity.OrderEntity;
import org.junit.Test;
import org.modelmapper.internal.util.Iterables;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrderMapperTest {

    @Test
    public void shouldTransformSingleOrder() {
        OrderEntity orderEntity = TestUtils.createTestOrder();

        Order dtoOrder = OrderMapper.INSTANCE.map(orderEntity);

        assertEquals(orderEntity.getId(), dtoOrder.getId());
        assertEquals(orderEntity.getClient().getId(), dtoOrder.getClient().getId());
        assertEquals(orderEntity.getProducts().size(), dtoOrder.getProducts().size());
        assertEquals(orderEntity.getOrderState().toString(), dtoOrder.getOrderState().toString());
        assertEquals(orderEntity.getDate(), dtoOrder.getDate());
    }

    @Test
    public void shouldTransformIterableWithOrders() {
        List<OrderEntity> orderEntityList = new ArrayList<>();
        orderEntityList.add(TestUtils.createTestOrder());
        orderEntityList.add(TestUtils.createTestOrder());
        orderEntityList.add(TestUtils.createTestOrder());

        Iterable<Order> ordersIterable = OrderMapper.INSTANCE.map(orderEntityList);

        assertEquals(Iterables.getLength(ordersIterable), orderEntityList.size());

        OrderEntity firstOrderEntity = orderEntityList.get(0);
        Order firstOrderDto = ordersIterable.iterator().next();

        assertEquals(firstOrderEntity.getId(), firstOrderDto.getId());
        assertEquals(firstOrderEntity.getClient().getId(), firstOrderDto.getClient().getId());
        assertEquals(firstOrderEntity.getDate(), firstOrderDto.getDate());
        assertEquals(firstOrderEntity.getOrderState().toString(), firstOrderDto.getOrderState().toString());
        assertEquals(firstOrderEntity.getProducts().get(0).getProduct().getCode(),
                firstOrderDto.getProducts().get(0).getProduct().getCode());
        assertNotNull(firstOrderDto.getTotal());
    }
}