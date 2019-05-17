package com.md.ecommerce.inventoryservice.controller;

import com.md.ecommerce.inventoryservice.domain.Product;
import com.md.ecommerce.inventoryservice.mapper.ProductMapper;
import com.md.ecommerce.inventoryservice.service.ProductService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController()
@RequestMapping("api/inventory/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    private ProductMapper productMapper;

    @GetMapping("/{code}")
    public Product findByCode(@PathVariable ("code") String code) {
        return Mappers.getMapper(ProductMapper.class).productEntityToProduct(productService.findByCode(code));
    }

    @GetMapping("")
    public List<Product> findAll() {
        return Mappers.getMapper(ProductMapper.class).productEntityListToProductEntityList(productService.findAll());
    }

    @PostMapping
    public Product save(@RequestBody Product product) {
        return Mappers.getMapper(ProductMapper.class).productEntityToProduct(
                productService.save(Mappers.getMapper(ProductMapper.class).productToProductEntity(product)));
    }
}
