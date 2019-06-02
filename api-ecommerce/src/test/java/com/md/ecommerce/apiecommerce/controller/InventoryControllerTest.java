package com.md.ecommerce.apiecommerce.controller;

import com.md.ecommerce.apiecommerce.TestUtils;
import com.md.ecommerce.apiecommerce.client.InventoryServiceClient;
import com.md.ecommerce.commons.dto.Product;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class InventoryControllerTest {

    private static final String PRODUCTS_RESOURCE_BASE_PATH = "/api/ecommerce/products/";

    @Mock
    private InventoryServiceClient inventoryServiceClient;

    @InjectMocks
    private InventoryController inventoryController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(inventoryController).build();
    }

    @Test
    public void shouldGetAllProducts() throws Exception {
        List<Product> productList = new ArrayList<>();
        productList.add(TestUtils.createTestProduct("123"));
        productList.add(TestUtils.createTestProduct("321"));

        when(inventoryServiceClient.getAllProducts()).thenReturn(productList);

        mockMvc.perform(get(PRODUCTS_RESOURCE_BASE_PATH))
                .andExpect(jsonPath("$.[0].code").value("123"))
                .andExpect(jsonPath("$.[1].code").value("321"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetProductByCode() throws Exception {
        Product product = TestUtils.createTestProduct("123");

        when(inventoryServiceClient.findProductByCode(product.getCode())).thenReturn(product);

        mockMvc.perform(get(PRODUCTS_RESOURCE_BASE_PATH + product.getCode()))
                .andExpect(jsonPath("$.code").value(product.getCode()))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldCreateProduct() throws Exception {
        Product product = TestUtils.createTestProduct("123");

        when(inventoryServiceClient.saveProduct(product)).thenReturn(product);

        mockMvc.perform(post(PRODUCTS_RESOURCE_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"code\": \"123\",\n" +
                        "    \"name\": \"testing product\",\n" +
                        "    \"description\": \"a testing product\",\n" +
                        "    \"price\": {\n" +
                        "        \"amount\": 25.5\n" +
                        "    },\n" +
                        "    \"stock\": 12\n" +
                        "}"))
                .andExpect(jsonPath("$.code").value("123"))
                .andExpect(jsonPath("$.name").value("testing product"))
                .andExpect(jsonPath("$.description").value("a testing product"))
                .andExpect(jsonPath("$.price.amount").value("25.5"))
                .andExpect(jsonPath("$.stock").value("12"));
    }
}