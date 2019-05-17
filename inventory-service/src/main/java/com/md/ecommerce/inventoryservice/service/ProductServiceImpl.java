package com.md.ecommerce.inventoryservice.service;

import com.md.ecommerce.inventoryservice.entity.ProductEntity;
import com.md.ecommerce.inventoryservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductEntity findByCode(String code) {
        return productRepository.findByCode(code);
    }

    @Override
    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    @Override
    public ProductEntity save(ProductEntity product) {
        return productRepository.save(product);
    }
}
