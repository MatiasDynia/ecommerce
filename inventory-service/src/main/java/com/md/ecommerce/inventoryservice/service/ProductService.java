package com.md.ecommerce.inventoryservice.service;

import com.md.ecommerce.inventoryservice.domain.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();
    Product save(Product product);
    Product findByCode(String code);

}
