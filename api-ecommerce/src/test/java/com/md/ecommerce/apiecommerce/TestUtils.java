package com.md.ecommerce.apiecommerce;

import com.md.ecommerce.commons.dto.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    public static Client createTestClient(String id) {
        return Client.builder()
                .id(id)
                .firstName("john")
                .lastName("wolf")
                .address("fake address 123")
                .phone("9876543")
                .build();
    }

    public static Product createTestProduct(String code) {
        return Product.builder()
                .code(code)
                .name("testing product")
                .description("a testing product")
                .price(Price.builder().amount(25.5).build())
                .stock(12)
                .build();
    }

    public static Order createTestOrder(String orderId) {
        Client orderClient = Client.builder()
                .id("12345")
                .firstName("John")
                .lastName("Doe")
                .address("fake address 123")
                .phone("123456789")
                .build();

        List<OrderProduct> productList = new ArrayList<>();
        productList.add(OrderProduct.builder()
                .product(Product.builder()
                        .code("123")
                        .name("product 1")
                        .description("description product 1")
                        .price(Price.builder().amount(15.5).build())
                        .stock(12)
                        .build())
                .quantity(2)
                .build());
        productList.add(OrderProduct.builder()
                .product(Product.builder()
                        .code("345")
                        .name("product 2")
                        .description("description product 2")
                        .price(Price.builder().amount(45.0).build())
                        .stock(5)
                        .build())
                .quantity(3)
                .build());

        return Order.builder()
                .id(orderId)
                .client(orderClient)
                .products(productList)
                .orderState(OrderState.PENDING)
                .date(LocalDate.of(2019,5,31))
                .total(166.0)
                .build();
    }
}