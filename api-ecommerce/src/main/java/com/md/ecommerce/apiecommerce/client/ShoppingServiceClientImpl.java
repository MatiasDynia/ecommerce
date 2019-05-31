package com.md.ecommerce.apiecommerce.client;

import com.md.ecommerce.commons.dto.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class ShoppingServiceClientImpl implements ShoppingServiceClient {

    public static final String SHOPPING_SERVICE_HOST = "http://localhost:9003";
    public static final String BASE_SHOPPING_ORDERS_SERVICE_URL = "/api/shopping/orders/";

    private final RestTemplate restTemplate;

    @Autowired
    public ShoppingServiceClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Order> getAllOrders() {
        ResponseEntity<List<Order>> responseEntity = restTemplate
                .exchange(SHOPPING_SERVICE_HOST + BASE_SHOPPING_ORDERS_SERVICE_URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Order>>() {
                        });

        return responseEntity.getBody();
    }

    @Override
    public Order findOrderById(String id) {

        return restTemplate.getForObject(SHOPPING_SERVICE_HOST + BASE_SHOPPING_ORDERS_SERVICE_URL + "{id}",
                Order.class,
                id);
    }

    @Override
    public Order saveOrder(Order order) {
        return restTemplate.postForObject(SHOPPING_SERVICE_HOST + BASE_SHOPPING_ORDERS_SERVICE_URL,
                order,
                Order.class);
    }
}
