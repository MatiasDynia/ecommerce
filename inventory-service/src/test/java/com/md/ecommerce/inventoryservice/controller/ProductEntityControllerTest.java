package com.md.ecommerce.inventoryservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.md.ecommerce.inventoryservice.domain.Price;
import com.md.ecommerce.inventoryservice.domain.Product;
import com.md.ecommerce.inventoryservice.entity.PriceEntity;
import com.md.ecommerce.inventoryservice.entity.ProductEntity;
import com.md.ecommerce.inventoryservice.mapper.ProductMapper;
import com.md.ecommerce.inventoryservice.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductEntityControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private ProductMapper productMapper;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void shouldGetProductByCode() throws Exception {
        final ProductEntity productEntity = new ProductEntity();
        productEntity.setCode("12345");
        final Product product = new Product();
        product.setCode("12345");

        when(productService.findByCode(productEntity.getCode())).thenReturn(productEntity);
        when(productMapper.productEntityToProduct(productEntity)).thenReturn(product);

        mockMvc.perform(get("/api/inventory/products/" + productEntity.getCode()))
                .andExpect(jsonPath("$.code").value(productEntity.getCode()))
                .andExpect(status().isOk());
    }

}