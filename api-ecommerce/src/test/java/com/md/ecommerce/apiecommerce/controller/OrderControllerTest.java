package com.md.ecommerce.apiecommerce.controller;

import com.md.ecommerce.apiecommerce.domain.OrderRequest;
import com.md.ecommerce.apiecommerce.TestUtils;
import com.md.ecommerce.apiecommerce.client.ShoppingServiceClient;
import com.md.ecommerce.commons.dto.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderControllerTest {

    private static final String ORDERS_RESOURCE_BASE_PATH = "/api/ecommerce/orders/";

    @Mock
    private ShoppingServiceClient shoppingServiceClient;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;


    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void getAllOrders() throws Exception {
        Order firstOrder = TestUtils.createTestOrder("1");
        Order secondOrder = TestUtils.createTestOrder("2");

        List<Order> orderList = new ArrayList<Order>() {{
            add(firstOrder);
            add(secondOrder);
        }};

        when(shoppingServiceClient.getAllOrders()).thenReturn(orderList);

        mockMvc.perform(get(ORDERS_RESOURCE_BASE_PATH))
                .andExpect(jsonPath("$.[0].id").value(firstOrder.getId()))
                .andExpect(jsonPath("$.[1].id").value(secondOrder.getId()));
    }

    @Test
    public void findOrderById() throws Exception {
        Order order = TestUtils.createTestOrder("1");

        when(shoppingServiceClient.findOrderById(order.getId())).thenReturn(order);

        mockMvc.perform(get(ORDERS_RESOURCE_BASE_PATH + order.getId()))
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.client.id").value(order.getClient().getId()))
                .andExpect(jsonPath("$.products.[0].product.code")
                        .value(order.getProducts().get(0).getProduct().getCode()))
                .andExpect(jsonPath("$.products.[1].product.code")
                        .value(order.getProducts().get(1).getProduct().getCode()))
                .andExpect(jsonPath("$.total").value("166.0"));
    }

    @Test
    public void createOrder() throws Exception {
        String jsonOrderRequest ="{\n" +
                "\t\"client\" : \"12345\",\n" +
                "\t\"products\" : {\n" +
                "\t\t\"123\" : 2,\n" +
                "\t\t\"345\" : 3\n" +
                "\t}\n" +
                "}";

        OrderRequest orderRequest = OrderRequest.builder()
                .client("12345")
                .products(new HashMap<String, Integer>() {{
                    put("123", 2);
                    put("345", 3);
                }})
                .build();

        Order order = TestUtils.createTestOrder("1");

        when(shoppingServiceClient.createOrder(orderRequest)).thenReturn(order);

        mockMvc.perform(post(ORDERS_RESOURCE_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonOrderRequest))
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.client.id").value(orderRequest.getClient()))
                .andExpect(jsonPath("$.products[0].product.code").value("123"))
                .andExpect(jsonPath("$.products[1].product.code").value("345"));
    }
}