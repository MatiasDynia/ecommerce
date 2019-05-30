package com.md.ecommerce.shoppingservice.controller;

import com.md.ecommerce.commons.dto.Order;
import com.md.ecommerce.shoppingservice.TestUtils;
import com.md.ecommerce.shoppingservice.entity.OrderEntity;
import com.md.ecommerce.shoppingservice.service.OrderService;
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
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        List<Order> orders = new ArrayList<>();
        orders.add(TestUtils.createTestOrderDto());
        orders.add(TestUtils.createTestOrderDto());
        orders.add(TestUtils.createTestOrderDto());

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
        Order order = TestUtils.createTestOrderDto();

        when(orderService.findById(order.getId())).thenReturn(order);

        mockMvc.perform(get("/api/shopping/order/" + order.getId()))
                .andExpect(jsonPath("$.products.[0].product.code")
                        .value(order.getProducts().get(0).getProduct().getCode()))
                .andExpect(jsonPath("$.products.[1].product.code")
                        .value(order.getProducts().get(1).getProduct().getCode()))
                .andExpect(jsonPath("$.client.id").value(order.getClient().getId()))
                .andExpect(jsonPath("$.total").value("166.0"));
    }

    @Test
    public void shouldSaveOrder() throws Exception {
        OrderEntity order = TestUtils.createTestOrder();
        Order orderToReturn = TestUtils.createTestOrderDto();

        when(orderService.saveOrder(order)).thenReturn(orderToReturn);

        mockMvc.perform(post("/api/shopping/order")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\n" +
                "\t\"id\":\"1\",\n" +
                "\t\"products\":[\n" +
                "\t\t{\n" +
                "\t\t\t\"product\":{\n" +
                "\t\t\t\t\"code\":\"123\",\n" +
                "\t\t\t\t\"name\":\"product 1\",\n" +
                "\t\t\t\t\"description\":\"description product 1\",\n" +
                "\t\t\t\t\"price\":\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\t\"amount\":15.5\n" +
                "\t\t\t\t}\n" +
                "\t\t\t\t,\"stock\":12\n" +
                "\t\t\t},\n" +
                "\t\t\t\"quantity\":2\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"product\":{\n" +
                "\t\t\t\t\"code\":\"345\",\n" +
                "\t\t\t\t\"name\":\"product 2\",\n" +
                "\t\t\t\t\"description\":\"description product 2\",\n" +
                "\t\t\t\t\"price\":{\n" +
                "\t\t\t\t\t\"amount\":45.0\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"stock\":5},\n" +
                "\t\t\t\"quantity\":3\n" +
                "\t\t}],\n" +
                "\t\"client\":{\n" +
                "\t\t\"id\":\"12345\",\n" +
                "\t\t\"firstName\":\"John\",\n" +
                "\t\t\"lastName\":\"Doe\",\n" +
                "\t\t\"address\":\"fake address 123\",\n" +
                "\t\t\"phone\":\"123456789\"},\n" +
                "\t\"orderState\":\"PREPARING\",\n" +
                "\t\"date\":[2019,5,30]\n" +
                "}"))
                .andExpect(jsonPath("$.products.[0].product.code")
                        .value(order.getProducts().get(0).getProduct().getCode()))
                .andExpect(jsonPath("$.products.[1].product.code")
                        .value(order.getProducts().get(1).getProduct().getCode()))
                .andExpect(jsonPath("$.client.id").value(order.getClient().getId()))
                .andExpect(status().isOk());
    }
}