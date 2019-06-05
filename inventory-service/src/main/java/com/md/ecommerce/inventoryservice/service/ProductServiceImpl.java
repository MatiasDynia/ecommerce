package com.md.ecommerce.inventoryservice.service;

import com.md.ecommerce.inventoryservice.entity.ProductEntity;
import com.md.ecommerce.inventoryservice.exception.ProductNotFoundException;
import com.md.ecommerce.inventoryservice.repository.ProductRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
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
        log.info("Retrieving product " + code + "...");

        Optional<ProductEntity> productFound = productRepository.findByCode(code);

        if(productFound.isPresent()) {
            log.info("Product " + code + "found!");

            return productFound.get();
        } else {
            throw new ProductNotFoundException("Product " + code + "not found!");
        }
    }

    @Override
    @HystrixCommand
    public Iterable<ProductEntity> findAll() {
        log.info("Retrieving all products...");

        return productRepository.findAll();
    }

    @Override
    @HystrixCommand
    public ProductEntity save(ProductEntity product) {
        log.info("Saving new product...");

        ProductEntity productCreated = productRepository.save(product);

        log.info("Product " + product.getCode() + " successfully saved!");

        return productCreated;
    }
}
