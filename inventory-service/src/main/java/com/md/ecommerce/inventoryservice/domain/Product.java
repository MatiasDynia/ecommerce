package com.md.ecommerce.inventoryservice.domain;

import lombok.Data;

@Data
public class Product {

    private String code;
    private String name;
    private String description;
    private Price price;
    private int stock;
}
