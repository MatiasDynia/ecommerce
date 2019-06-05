package com.md.ecommerce.apiecommerce.client;

import com.md.ecommerce.apiecommerce.domain.OrderRequest;
import com.md.ecommerce.commons.dto.Client;
import com.md.ecommerce.commons.dto.Order;
import com.md.ecommerce.commons.dto.OrderProduct;
import com.md.ecommerce.commons.dto.OrderState;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ShoppingServiceClientImpl implements ShoppingServiceClient {

    static final String SHOPPING_SERVICE_HOST = "http://shopping-service";
    static final String BASE_SHOPPING_ORDERS_SERVICE_URL = "/api/shopping/orders/";

    private final InventoryServiceClient inventoryServiceClient;
    private final ClientServiceClient clientServiceClient;
    private final RestTemplate restTemplate;

    @Autowired
    public ShoppingServiceClientImpl(InventoryServiceClient inventoryServiceClient,
                                     ClientServiceClient clientServiceClient,
                                     RestTemplate restTemplate) {
        this.inventoryServiceClient = inventoryServiceClient;
        this.clientServiceClient = clientServiceClient;
        this.restTemplate = restTemplate;
    }

    @Override
    @HystrixCommand
    public List<Order> getAllOrders() {
        log.info("Getting all orders...");

        ResponseEntity<List<Order>> responseEntity = restTemplate
                .exchange(SHOPPING_SERVICE_HOST + BASE_SHOPPING_ORDERS_SERVICE_URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Order>>() {
                        });

        return responseEntity.getBody();
    }

    @Override
    @HystrixCommand
    public Order findOrderById(String id) {
        log.info("Finding order " + id + "...");

        return restTemplate.getForObject(SHOPPING_SERVICE_HOST + BASE_SHOPPING_ORDERS_SERVICE_URL + "{id}",
                Order.class,
                id);
    }

    @Override
    @HystrixCommand
    public Order createOrder(OrderRequest orderRequest) {
        log.info("Placing new order...");

        List<OrderProduct> orderProducts = new ArrayList<>();

        for (Map.Entry<String, Integer> orderProductEntry : orderRequest.getProducts().entrySet()) {
            orderProducts.add(OrderProduct.builder()
                    .product(inventoryServiceClient.findProductByCode(orderProductEntry.getKey()))
                    .quantity(orderProductEntry.getValue())
                    .build());
        }

        Client orderClient = clientServiceClient.findClientById(orderRequest.getClient());

        Order orderToSave = Order.builder()
                .client(orderClient)
                .products(orderProducts)
                .orderState(OrderState.PENDING)
                .date(LocalDate.now())
                .build();

        return restTemplate.postForObject(SHOPPING_SERVICE_HOST + BASE_SHOPPING_ORDERS_SERVICE_URL,
                orderToSave,
                Order.class);
    }
}
