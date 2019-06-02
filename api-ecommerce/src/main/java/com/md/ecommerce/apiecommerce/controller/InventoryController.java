package com.md.ecommerce.apiecommerce.controller;

import com.md.ecommerce.apiecommerce.client.InventoryServiceClient;
import com.md.ecommerce.commons.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ecommerce/products")
public class InventoryController {

    private InventoryServiceClient inventoryServiceClient;

    @Autowired
    public InventoryController(InventoryServiceClient inventoryServiceClient) {
        this.inventoryServiceClient = inventoryServiceClient;
    }

    @GetMapping("")
    public List<Product> getAllProducts() {
        return inventoryServiceClient.getAllProducts();
    }

    @GetMapping("/{ProductCode}")
    public Product getProductByCode(@PathVariable String ProductCode) {
        return inventoryServiceClient.findProductByCode(ProductCode);
    }

    @PostMapping
    public Product saveProduct(@RequestBody Product product) {
        return inventoryServiceClient.saveProduct(product);
    }
}
