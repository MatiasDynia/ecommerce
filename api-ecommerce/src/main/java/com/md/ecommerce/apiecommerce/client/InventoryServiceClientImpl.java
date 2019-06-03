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
public class InventoryServiceClientImpl implements InventoryServiceClient {

    static final String INVENTORY_SERVICE_HOST = "http://inventory-service";
    static final String BASE_INVENTORY_PRODUCTS_SERVICE_URL = "/api/inventory/products/";

    private final RestTemplate restTemplate;

    @Autowired
    public InventoryServiceClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Product> getAllProducts() {
        ResponseEntity<List<Product>> responseEntity = restTemplate
                .exchange(INVENTORY_SERVICE_HOST + BASE_INVENTORY_PRODUCTS_SERVICE_URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Product>>() {
                        });

        return responseEntity.getBody();
    }

    @Override
    public Product findProductByCode(String code) {

        return restTemplate
                .getForObject(INVENTORY_SERVICE_HOST + BASE_INVENTORY_PRODUCTS_SERVICE_URL +"{code}",
                        Product.class,
                        code);
    }

    @Override
    public Product saveProduct(Product product) {

        return restTemplate
                .postForObject(INVENTORY_SERVICE_HOST + BASE_INVENTORY_PRODUCTS_SERVICE_URL,
                        product,
                        Product.class);
    }
}
