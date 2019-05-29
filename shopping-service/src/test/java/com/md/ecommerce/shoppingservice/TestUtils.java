package com.md.ecommerce.shoppingservice;

import com.md.ecommerce.commons.dto.Client;
import com.md.ecommerce.commons.dto.Price;
import com.md.ecommerce.commons.dto.Product;
import com.md.ecommerce.shoppingservice.entity.OrderEntity;
import com.md.ecommerce.shoppingservice.entity.OrderProductEntity;
import com.md.ecommerce.shoppingservice.entity.OrderStateEntity;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    public static OrderEntity createTestOrder() {
        Client orderClient = Client.builder()
                .id("12345")
                .firstName("John")
                .lastName("Doe")
                .address("fake address 123")
                .phone("123456789")
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
                ._id(new ObjectId("58d1c36efb0cac4e15afd278"))
                .client(orderClient)
                .products(productList)
                .orderState(OrderStateEntity.PREPARING)
                .date(LocalDate.now())
                .build();
    }
}
