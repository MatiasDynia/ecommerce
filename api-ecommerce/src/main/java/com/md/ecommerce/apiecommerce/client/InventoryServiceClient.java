package com.md.ecommerce.apiecommerce.client;

import com.md.ecommerce.commons.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class InventoryServiceClient {

    private final RestTemplate restTemplate;

    @Autowired
    public InventoryServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Product> getAllProducts() {
        ResponseEntity<List<Product>> responseEntity = restTemplate
                .exchange("http://localhost:9001/api/inventory/products",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Product>>() {
                        });

        return responseEntity.getBody();
    }

    public Product getProductByCode(String code) {

        return restTemplate
                .getForObject("http://localhost:9001/api/inventory/products/{code}",
                        Product.class,
                        code);
    }

    public Product saveProduct(Product product) {

        return restTemplate
                .postForObject("http://localhost:9001/api/inventory/products",
                        product,
                        Product.class);
    }
}
