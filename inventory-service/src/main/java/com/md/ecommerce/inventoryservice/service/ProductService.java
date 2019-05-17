package com.md.ecommerce.inventoryservice.service;

import com.md.ecommerce.inventoryservice.domain.Product;
import com.md.ecommerce.inventoryservice.entity.ProductEntity;

import java.util.List;

public interface ProductService {

    List<ProductEntity> findAll();
    ProductEntity save(ProductEntity product);
    ProductEntity findByCode(String code);

}
