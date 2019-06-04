package com.md.ecommerce.inventoryservice.service;

import com.md.ecommerce.inventoryservice.entity.ProductEntity;
import com.md.ecommerce.inventoryservice.repository.ProductRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @HystrixCommand
    public ProductEntity findByCode(String code) {
        return productRepository.findByCode(code);
    }

    @Override
    @HystrixCommand
    public Iterable<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    @Override
    @HystrixCommand
    public ProductEntity save(ProductEntity product) {
        return productRepository.save(product);
    }
}
