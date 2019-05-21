package com.md.ecommerce.inventoryservice.service;

import com.md.ecommerce.inventoryservice.entity.PriceEntity;
import com.md.ecommerce.inventoryservice.entity.ProductEntity;
import com.md.ecommerce.inventoryservice.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void shouldFindByCode() {

        ProductEntity product = ProductEntity.builder().code("12345").build();

        when(productRepository.findByCode(product.getCode())).thenReturn(product);

        ProductEntity productFound = productService.findByCode(product.getCode());

        assertEquals(product.getCode(), productFound.getCode());
        verify(productRepository).findByCode(product.getCode());
    }

    @Test
    public void shouldFindAllProducts() {

        List<ProductEntity> listOfProducts = new ArrayList<>();
        listOfProducts.add(ProductEntity.builder().code("1").build());
        listOfProducts.add(ProductEntity.builder().code("2").build());
        listOfProducts.add(ProductEntity.builder().code("3").build());

        when(productRepository.findAll()).thenReturn(listOfProducts);

        Iterable<ProductEntity> productsFound = productService.findAll();

        assertEquals(listOfProducts, productsFound);
        verify(productRepository).findAll();
    }

    @Test
    public void shouldCreateProduct() {

        ProductEntity product = ProductEntity.builder()
                .code("12345")
                .name("testing product")
                .description("a testing product")
                .price(PriceEntity.builder().amount(25.5).build())
                .stock(12)
                .build();

        when(productRepository.save(product)).thenReturn(product);

        ProductEntity productSaved = productRepository.save(product);

        assertEquals(product, productSaved);
        verify(productRepository).save(product);
    }
}