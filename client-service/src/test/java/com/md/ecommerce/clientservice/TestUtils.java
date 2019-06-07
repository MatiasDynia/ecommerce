package com.md.ecommerce.clientservice;

import com.md.ecommerce.clientservice.entity.ClientEntity;

public class TestUtils {

    public static ClientEntity createTestClient(String id) {
        return ClientEntity.builder()
                .id(id)
                .firstName("john")
                .lastName("wolf")
                .address("fake address 123")
                .phone("9876543")
                .mail("test@mail.com")
                .build();
    }
}