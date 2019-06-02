package com.md.ecommerce.apiecommerce.client;

import com.md.ecommerce.commons.dto.Product;

import java.util.List;

public interface InventoryServiceClient {

    public List<Product> getAllProducts();
    public Product findProductByCode(String code);
    public Product saveProduct(Product product);
}
