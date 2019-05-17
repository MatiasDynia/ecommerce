package com.md.ecommerce.inventoryservice.controller;

import com.md.ecommerce.inventoryservice.domain.Product;
import com.md.ecommerce.inventoryservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController()
@RequestMapping("api/inventory/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{code}")
    public Product findByCode(@PathVariable ("code") String code) {
        return productService.findByCode(code);
    }

    @GetMapping("")
    public List<Product> findAll() {
        return productService.findAll();
    }

    @PostMapping
    public Product save(@RequestBody Product product) {
        return productService.save(product);
    }
}
