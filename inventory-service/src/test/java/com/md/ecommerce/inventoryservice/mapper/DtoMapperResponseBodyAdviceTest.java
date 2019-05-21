package com.md.ecommerce.inventoryservice.mapper;

import com.md.ecommerce.inventoryservice.controller.ProductController;
import com.md.ecommerce.inventoryservice.entity.PriceEntity;
import com.md.ecommerce.inventoryservice.entity.ProductEntity;
import com.md.ecommerce.inventoryservice.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class DtoMapperResponseBodyAdviceTest {

    //TO DO: Ensure this is the best way to test this.
    //is it correct to need to change the mapper advice tests if dto objects change?

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    DtoMapperResponseBodyAdvice dtoMapperResponseBodyAdvice;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setControllerAdvice(dtoMapperResponseBodyAdvice).build();
    }

    @Test
    public void shouldMapProductByCodeCorrectlyToDto() throws Exception {
        ProductEntity productEntity = ProductEntity.builder()
                .id(3L)
                .code("12345")
                .name("testing product")
                .description("a testing product")
                .price(PriceEntity.builder().id(5L).amount(25.5).build())
                .stock(12)
                .build();

        when(productService.findByCode(productEntity.getCode())).thenReturn(productEntity);

        mockMvc.perform(get("/api/inventory/products/" + productEntity.getCode()))
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.code").value(productEntity.getCode()))
                .andExpect(jsonPath("$.name").value(productEntity.getName()))
                .andExpect(jsonPath("$.description").value(productEntity.getDescription()))
                .andExpect(jsonPath("$.price.id").doesNotExist())
                .andExpect(jsonPath("$.price.amount").value(productEntity.getPrice().getAmount()))
                .andExpect(jsonPath("$.stock").value(productEntity.getStock()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldMapAllProductsCorrectlyToDto() throws Exception {
        List<ProductEntity> listOfProducts = new ArrayList<>();
        listOfProducts.add(ProductEntity.builder()
                .code("12345")
                .name("testing product")
                .description("a testing product")
                .price(PriceEntity.builder().amount(25.5).build())
                .stock(12)
                .build());

        listOfProducts.add(ProductEntity.builder()
                .code("12345")
                .name("testing product")
                .description("a testing product")
                .price(PriceEntity.builder().amount(25.5).build())
                .stock(12)
                .build());

        listOfProducts.add(ProductEntity.builder()
                .code("12345")
                .name("testing product")
                .description("a testing product")
                .price(PriceEntity.builder().amount(25.5).build())
                .stock(12)
                .build());

        when(productService.findAll()).thenReturn(listOfProducts);

        mockMvc.perform(get("/api/inventory/products"))
                .andExpect(jsonPath("$.[0].id").doesNotExist())
                .andExpect(jsonPath("$.[1].id").doesNotExist())
                .andExpect(jsonPath("$.[2].id").doesNotExist())
                .andExpect(jsonPath("$.[0].code").value(listOfProducts.get(0).getCode()))
                .andExpect(jsonPath("$.[1].code").value(listOfProducts.get(1).getCode()))
                .andExpect(jsonPath("$.[2].code").value(listOfProducts.get(2).getCode()))
                .andExpect(jsonPath("$.[0].name").value(listOfProducts.get(0).getName()))
                .andExpect(jsonPath("$.[1].name").value(listOfProducts.get(1).getName()))
                .andExpect(jsonPath("$.[2].name").value(listOfProducts.get(2).getName()))
                .andExpect(jsonPath("$.[0].description").value(listOfProducts.get(0).getDescription()))
                .andExpect(jsonPath("$.[1].description").value(listOfProducts.get(1).getDescription()))
                .andExpect(jsonPath("$.[2].description").value(listOfProducts.get(2).getDescription()))
                .andExpect(jsonPath("$.[0].price.id").doesNotExist())
                .andExpect(jsonPath("$.[1].price.id").doesNotExist())
                .andExpect(jsonPath("$.[2].price.id").doesNotExist())
                .andExpect(jsonPath("$.[0].price.amount").value(listOfProducts.get(0).getPrice().getAmount()))
                .andExpect(jsonPath("$.[1].price.amount").value(listOfProducts.get(1).getPrice().getAmount()))
                .andExpect(jsonPath("$.[2].price.amount").value(listOfProducts.get(2).getPrice().getAmount()))
                .andExpect(jsonPath("$.[0].stock").value(listOfProducts.get(0).getStock()))
                .andExpect(jsonPath("$.[1].stock").value(listOfProducts.get(1).getStock()))
                .andExpect(jsonPath("$.[2].stock").value(listOfProducts.get(2).getStock()))
                .andExpect(status().isOk());
    }
}