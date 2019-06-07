package com.md.ecommerce.shoppingservice;

import com.md.ecommerce.commons.dto.Client;
import com.md.ecommerce.commons.dto.Order;
import com.md.ecommerce.commons.dto.Price;
import com.md.ecommerce.commons.dto.Product;
import com.md.ecommerce.shoppingservice.entity.OrderEntity;
import com.md.ecommerce.shoppingservice.entity.OrderProductEntity;
import com.md.ecommerce.shoppingservice.entity.OrderStateEntity;
import com.md.ecommerce.shoppingservice.mapper.OrderMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    public static Order createTestOrderDto() {
        return OrderMapper.INSTANCE.map(createTestOrder());
    }

    public static OrderEntity createTestOrder() {
        Client orderClient = Client.builder()
                .id("12345")
                .firstName("John")
                .lastName("Doe")
                .address("fake address 123")
                .phone("123456789")
                .mail("test@mail.com")
                .build();

        List<OrderProductEntity> productList = new ArrayList<>();
        productList.add(OrderProductEntity.builder()
                .product(Product.builder()
                        .code("123")
                        .name("product 1")
                        .description("description product 1")
                        .price(Price.builder().amount(15.5).build())
                        .stock(12)
                        .build())
                .quantity(2)
                .build());
        productList.add(OrderProductEntity.builder()
                .product(Product.builder()
                        .code("345")
                        .name("product 2")
                        .description("description product 2")
                        .price(Price.builder().amount(45.0).build())
                        .stock(5)
                        .build())
                .quantity(3)
                .build());

        return OrderEntity.builder()
                .id("1")
                .client(orderClient)
                .products(productList)
                .orderState(OrderStateEntity.PREPARING)
                .date(LocalDate.of(2019,5,31))
                .build();
    }
}
