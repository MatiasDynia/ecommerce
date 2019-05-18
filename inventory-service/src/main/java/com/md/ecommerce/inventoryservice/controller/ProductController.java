package com.md.ecommerce.inventoryservice.controller;

import com.md.ecommerce.inventoryservice.domain.Product;
import com.md.ecommerce.inventoryservice.entity.ProductEntity;
import com.md.ecommerce.inventoryservice.mapper.Dto;
import com.md.ecommerce.inventoryservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping("api/inventory/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{code}")
    @Dto(Product.class)
    public ProductEntity findByCode(@PathVariable ("code") String code) {
        return productService.findByCode(code);
    }

    @GetMapping("")
    @Dto(Product.class)
    public Iterable<ProductEntity> findAll() {
        return productService.findAll();
    }

    @PostMapping
    @Dto(Product.class)
    public ProductEntity save(@RequestBody ProductEntity product) {
        return productService.save(product);
    }
}
