package com.md.ecommerce.inventoryservice.service;

import com.md.ecommerce.inventoryservice.entity.ProductEntity;
import com.md.ecommerce.inventoryservice.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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

        final ProductEntity product = new ProductEntity();
        product.setCode("12345");

        when(productRepository.findByCode(product.getCode())).thenReturn(product);

        final ProductEntity productFound = productService.findByCode(product.getCode());

        assertEquals(product.getCode(), productFound.getCode());
        verify(productRepository).findByCode(product.getCode());
    }
}