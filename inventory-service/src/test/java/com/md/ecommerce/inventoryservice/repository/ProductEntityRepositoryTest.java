package com.md.ecommerce.inventoryservice.repository;

import com.md.ecommerce.inventoryservice.entity.PriceEntity;
import com.md.ecommerce.inventoryservice.entity.ProductEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductEntityRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void shouldFindProductByCode() {
        ProductEntity productToSave = getStubProductEntity();

        productRepository.save(productToSave);

        ProductEntity productFound = productRepository.findByCode(productToSave.getCode()).get();

        assertEquals(productToSave.getCode(), productFound.getCode());
        assertEquals(productToSave.getName(), productFound.getName());
        assertEquals(productToSave.getDescription(), productFound.getDescription());
        assertEquals(productToSave.getPrice().getAmount(), productFound.getPrice().getAmount());
        assertEquals(productToSave.getStock(), productFound.getStock());
    }

    private ProductEntity getStubProductEntity() {

        List<ProductEntity> listOfProducts = new ArrayList<>();
        listOfProducts.add(ProductEntity.builder().code("1").build());
        listOfProducts.add(ProductEntity.builder().code("2").build());
        listOfProducts.add(ProductEntity.builder().code("3").build());

        return ProductEntity.builder()
                .code("12345")
                .name("testing product")
                .description("a testing product")
                .price(PriceEntity.builder().amount(25.5).build())
                .stock(12)
                .build();
    }
}