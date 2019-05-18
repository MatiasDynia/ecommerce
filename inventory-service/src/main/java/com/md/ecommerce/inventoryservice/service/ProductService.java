package com.md.ecommerce.inventoryservice.service;

import com.md.ecommerce.inventoryservice.entity.ProductEntity;

public interface ProductService {

    Iterable<ProductEntity> findAll();
    ProductEntity save(ProductEntity product);
    ProductEntity findByCode(String code);

}
