package com.md.ecommerce.apiecommerce.client;

import com.md.ecommerce.apiecommerce.domain.OrderRequest;
import com.md.ecommerce.apiecommerce.TestUtils;
import com.md.ecommerce.commons.dto.Client;
import com.md.ecommerce.commons.dto.Order;
import com.md.ecommerce.commons.dto.OrderState;
import com.md.ecommerce.commons.dto.Product;
import org.assertj.core.api.BDDAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static com.md.ecommerce.apiecommerce.client.ShoppingServiceClientImpl.BASE_SHOPPING_ORDERS_SERVICE_URL;
import static com.md.ecommerce.apiecommerce.client.ShoppingServiceClientImpl.SHOPPING_SERVICE_HOST;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ShoppingServiceClientImplTest {

    @Mock
    private InventoryServiceClient inventoryServiceClient;

    @Mock
    private ClientServiceClient clientServiceClient;

    @InjectMocks
    private ShoppingServiceClientImpl shoppingServiceClient;

    private MockRestServiceServer server;

    @Before
    public void setup() {
        RestTemplate restTemplate = new RestTemplate();
        shoppingServiceClient = new ShoppingServiceClientImpl(inventoryServiceClient, clientServiceClient, restTemplate);
        server = MockRestServiceServer.bindTo(restTemplate).build();
    }

    @Test
    public void getAllOrders() {
        String orderListJson = "[\n" +
                "\t{\n" +
                "\t\t\"id\":\"1\",\n" +
                "\t\t\"products\":[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"product\":{\n" +
                "\t\t\t\t\t\"code\":\"123\",\n" +
                "\t\t\t\t\t\"name\":\"product 123\",\n" +
                "\t\t\t\t\t\"description\":\"description product 1\",\n" +
                "\t\t\t\t\t\"price\":\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"amount\":15.5\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t\t,\"stock\":12\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"quantity\":2\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"product\":{\n" +
                "\t\t\t\t\t\"code\":\"345\",\n" +
                "\t\t\t\t\t\"name\":\"product 345\",\n" +
                "\t\t\t\t\t\"description\":\"description product 2\",\n" +
                "\t\t\t\t\t\"price\":{\n" +
                "\t\t\t\t\t\t\"amount\":45.0\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t\"stock\":5},\n" +
                "\t\t\t\t\"quantity\":3\n" +
                "\t\t\t}],\n" +
                "\t\t\"client\":{\n" +
                "\t\t\t\"id\":\"12345\",\n" +
                "\t\t\t\"firstName\":\"John\",\n" +
                "\t\t\t\"lastName\":\"Doe\",\n" +
                "\t\t\t\"address\":\"fake address 123\",\n" +
                "\t\t\t\"phone\":\"123456789\"},\n" +
                "\t\t\"orderState\":\"PENDING\",\n" +
                "\t\t\"date\":[2019,5,30],\n" +
                "\t\t\"total\": 166\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"id\":\"2\",\n" +
                "\t\t\"products\":[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"product\":{\n" +
                "\t\t\t\t\t\"code\":\"321\",\n" +
                "\t\t\t\t\t\"name\":\"product 321\",\n" +
                "\t\t\t\t\t\"description\":\"description product 1\",\n" +
                "\t\t\t\t\t\"price\":\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"amount\":15.5\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t\t,\"stock\":12\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"quantity\":2\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"product\":{\n" +
                "\t\t\t\t\t\"code\":\"543\",\n" +
                "\t\t\t\t\t\"name\":\"product 543\",\n" +
                "\t\t\t\t\t\"description\":\"description product 2\",\n" +
                "\t\t\t\t\t\"price\":{\n" +
                "\t\t\t\t\t\t\"amount\":45.0\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t\"stock\":5},\n" +
                "\t\t\t\t\"quantity\":3\n" +
                "\t\t\t}],\n" +
                "\t\t\"client\":{\n" +
                "\t\t\t\"id\":\"54321\",\n" +
                "\t\t\t\"firstName\":\"Jane\",\n" +
                "\t\t\t\"lastName\":\"Doe\",\n" +
                "\t\t\t\"address\":\"fake address 321\",\n" +
                "\t\t\t\"phone\":\"987654321\"},\n" +
                "\t\t\"orderState\":\"PREPARING\",\n" +
                "\t\t\"date\":[2019,5,30],\n" +
                "\t\t\"total\": 166\n" +
                "\t}\n" +
                "]";

        server.expect(requestTo(SHOPPING_SERVICE_HOST + BASE_SHOPPING_ORDERS_SERVICE_URL))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(orderListJson, MediaType.APPLICATION_JSON));

        List<Order> orderList = shoppingServiceClient.getAllOrders();

        Order firstOrder = orderList.get(0);
        Order secondOrder = orderList.get(1);

        BDDAssertions.then(firstOrder.getId()).isEqualTo("1");
        BDDAssertions.then(firstOrder.getProducts().get(0).getProduct().getCode()).isEqualTo("123");
        BDDAssertions.then(firstOrder.getProducts().get(1).getProduct().getCode()).isEqualTo("345");
        BDDAssertions.then(firstOrder.getClient().getId()).isEqualTo("12345");
        BDDAssertions.then(firstOrder.getOrderState()).isEqualTo(OrderState.PENDING);
        BDDAssertions.then(firstOrder.getTotal()).isEqualTo(166);

        BDDAssertions.then(secondOrder.getId()).isEqualTo("2");
        BDDAssertions.then(secondOrder.getProducts().get(0).getProduct().getCode()).isEqualTo("321");
        BDDAssertions.then(secondOrder.getProducts().get(1).getProduct().getCode()).isEqualTo("543");
        BDDAssertions.then(secondOrder.getClient().getId()).isEqualTo("54321");
        BDDAssertions.then(secondOrder.getOrderState()).isEqualTo(OrderState.PREPARING);
        BDDAssertions.then(secondOrder.getTotal()).isEqualTo(166);
    }

    @Test
    public void findOrderById() {
        String orderJson = "{\n" +
                "    \"id\": \"1\",\n" +
                "    \"products\": [\n" +
                "        {\n" +
                "            \"product\": {\n" +
                "                \"code\": \"123\",\n" +
                "                \"name\": \"product 1\",\n" +
                "                \"description\": \"description product 1\",\n" +
                "                \"price\": {\n" +
                "                    \"amount\": 15.5\n" +
                "                },\n" +
                "                \"stock\": 12\n" +
                "            },\n" +
                "            \"quantity\": 2\n" +
                "        },\n" +
                "        {\n" +
                "            \"product\": {\n" +
                "                \"code\": \"345\",\n" +
                "                \"name\": \"product 2\",\n" +
                "                \"description\": \"description product 2\",\n" +
                "                \"price\": {\n" +
                "                    \"amount\": 45\n" +
                "                },\n" +
                "                \"stock\": 5\n" +
                "            },\n" +
                "            \"quantity\": 3\n" +
                "        }\n" +
                "    ],\n" +
                "    \"client\": {\n" +
                "        \"id\": \"12345\",\n" +
                "        \"firstName\": \"John\",\n" +
                "        \"lastName\": \"Doe\",\n" +
                "        \"address\": \"fake address 123\",\n" +
                "        \"phone\": \"123456789\"\n" +
                "    },\n" +
                "    \"orderState\": \"PREPARING\",\n" +
                "    \"date\": \"2019-05-30\",\n" +
                "    \"total\": 166\n" +
                "}";

        server.expect(requestTo(SHOPPING_SERVICE_HOST + BASE_SHOPPING_ORDERS_SERVICE_URL + "1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(orderJson, MediaType.APPLICATION_JSON));

        Order orderFound = shoppingServiceClient.findOrderById("1");

        BDDAssertions.then(orderFound.getId()).isEqualTo("1");
        BDDAssertions.then(orderFound.getProducts().get(0).getProduct().getCode()).isEqualTo("123");
        BDDAssertions.then(orderFound.getProducts().get(1).getProduct().getCode()).isEqualTo("345");
        BDDAssertions.then(orderFound.getClient().getId()).isEqualTo("12345");
        BDDAssertions.then(orderFound.getOrderState()).isEqualTo(OrderState.PREPARING);
        BDDAssertions.then(orderFound.getTotal()).isEqualTo(166);
    }

    @Test
    public void shouldCreateOrderByProductsAndClient() {
        String orderSavedJson = "{\n" +
                "\t\t\"id\":\"1\",\n" +
                "\t\t\"products\":[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"product\":{\n" +
                "\t\t\t\t\t\"code\":\"123\",\n" +
                "\t\t\t\t\t\"name\":\"product 123\",\n" +
                "\t\t\t\t\t\"description\":\"description product 1\",\n" +
                "\t\t\t\t\t\"price\":\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"amount\":15.5\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t\t,\"stock\":12\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"quantity\":2\n" +
                "\t\t\t}],\n" +
                "\t\t\"client\":{\n" +
                "\t\t\t\"id\":\"198\",\n" +
                "\t\t\t\"firstName\":\"John\",\n" +
                "\t\t\t\"lastName\":\"Doe\",\n" +
                "\t\t\t\"address\":\"fake address 123\",\n" +
                "\t\t\t\"phone\":\"123456789\"},\n" +
                "\t\t\"orderState\":\"PENDING\",\n" +
                "\t\t\"date\":[2019,5,30],\n" +
                "\t\t\"total\": 166\n" +
                "\t}";

        Product product = TestUtils.createTestProduct("123");
        Client client = TestUtils.createTestClient("198");

        OrderRequest orderRequest = OrderRequest.builder()
                .client(client.getId())
                .products(new HashMap<String, Integer>() {{
                    put(product.getCode(), 5);
                }})
                .build();

        when(clientServiceClient.findClientById(orderRequest.getClient())).thenReturn(client);
        when(inventoryServiceClient
                .findProductByCode(orderRequest.getProducts().entrySet().stream().findAny().get().getKey()))
                .thenReturn(product);

        server.expect(requestTo(SHOPPING_SERVICE_HOST + BASE_SHOPPING_ORDERS_SERVICE_URL))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(orderSavedJson, MediaType.APPLICATION_JSON));

        Order orderSaved = shoppingServiceClient.createOrder(orderRequest);

        BDDAssertions.then(orderSaved.getClient().getId()).isEqualTo(orderRequest.getClient());
        BDDAssertions.then(orderSaved.getProducts().get(0).getProduct().getCode())
                .isEqualTo(orderRequest.getProducts().entrySet().stream().findAny().get().getKey());
        BDDAssertions.then(orderSaved.getOrderState()).isEqualTo(OrderState.PENDING);
        BDDAssertions.then(orderSaved.getDate()).isEqualTo(LocalDate.of(2019,5,30));
        BDDAssertions.then(orderSaved.getTotal()).isEqualTo(166);
    }
}