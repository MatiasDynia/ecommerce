package com.md.ecommerce.inventoryservice.controller;

import com.md.ecommerce.commons.dto.Product;
import com.md.ecommerce.inventoryservice.entity.PriceEntity;
import com.md.ecommerce.inventoryservice.entity.ProductEntity;
import com.md.ecommerce.inventoryservice.service.ProductService;
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
public class ProductEntityControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void shouldGetProductByCode() throws Exception {
        ProductEntity productEntity = ProductEntity.builder().code("12345").build();
        Product product = Product.builder().code("12345").build();

        when(productService.findByCode(productEntity.getCode())).thenReturn(productEntity);

        mockMvc.perform(get("/api/inventory/products/" + product.getCode()))
                .andExpect(jsonPath("$.code").value(product.getCode()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFindAllProducts() throws Exception {
        List<ProductEntity> listOfProducts = new ArrayList<>();
        listOfProducts.add(ProductEntity.builder().code("1").build());
        listOfProducts.add(ProductEntity.builder().code("2").build());
        listOfProducts.add(ProductEntity.builder().code("3").build());

        when(productService.findAll()).thenReturn(listOfProducts);

        mockMvc.perform(get("/api/inventory/products"))
                .andExpect(jsonPath("$.[0].code").value("1"))
                .andExpect(jsonPath("$.[1].code").value("2"))
                .andExpect(jsonPath("$.[2].code").value("3"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSaveProduct() throws Exception {
        ProductEntity productEntity = ProductEntity.builder()
                .code("12345")
                .name("testing product")
                .description("a testing product")
                .price(PriceEntity.builder().amount(25.5).build())
                .stock(12)
                .build();

        when(productService.save(productEntity)).thenReturn(productEntity);

        mockMvc.perform(post("/api/inventory/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n" +
                            "    \"code\": \"12345\",\n" +
                            "    \"name\": \"testing product\",\n" +
                            "    \"description\": \"a testing product\",\n" +
                            "    \"price\": {\n" +
                            "        \"amount\": 25.5\n" +
                            "    },\n" +
                            "    \"stock\": 12\n" +
                            "}"))
                .andExpect(jsonPath("$.code").value("12345"))
                .andExpect(jsonPath("$.name").value("testing product"))
                .andExpect(jsonPath("$.description").value("a testing product"))
                .andExpect(jsonPath("$.price.amount").value("25.5"))
                .andExpect(jsonPath("$.stock").value("12"))
                .andExpect(status().isOk());
    }

}