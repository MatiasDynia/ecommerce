package com.md.ecommerce.shoppingservice.controller;

import com.md.ecommerce.shoppingservice.TestUtils;
import com.md.ecommerce.shoppingservice.entity.OrderEntity;
import com.md.ecommerce.shoppingservice.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    MockMvc mockMvc;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void shouldFindAllOrders() throws Exception {
        List<OrderEntity> orders = new ArrayList<>();
        orders.add(TestUtils.createTestOrder());
        orders.add(TestUtils.createTestOrder());
        orders.add(TestUtils.createTestOrder());

        when(orderService.findAllOrders()).thenReturn(orders);
        mockMvc.perform(get("/api/shopping/order"))
                .andExpect(jsonPath("$[0].client.id").value(orders.get(0).getClient().getId()))
                .andExpect(jsonPath("$[0].products.[0].product.code")
                        .value(orders.get(0).getProducts().get(0).getProduct().getCode()))
                .andExpect(jsonPath("$[0].products.[1].product.code")
                        .value(orders.get(0).getProducts().get(1).getProduct().getCode()))
                .andExpect(jsonPath("$[0].orderState").value(orders.get(0).getOrderState().toString()))
                .andExpect(jsonPath("$[1].client.id").value(orders.get(1).getClient().getId()))
                .andExpect(jsonPath("$[2].client.id").value(orders.get(2).getClient().getId()));

    }

    @Test
    public void shouldFindOrderById() throws Exception {
        OrderEntity order = TestUtils.createTestOrder();

        when(orderService.findById(order.getId())).thenReturn(order);

        mockMvc.perform(get("/api/shopping/order/" + order.getId()))
                .andExpect(jsonPath("$.products.[0].product.code")
                        .value(order.getProducts().get(0).getProduct().getCode()))
                .andExpect(jsonPath("$.products.[1].product.code")
                        .value(order.getProducts().get(1).getProduct().getCode()))
                .andExpect(jsonPath("$.client.id").value(order.getClient().getId()));
    }
}