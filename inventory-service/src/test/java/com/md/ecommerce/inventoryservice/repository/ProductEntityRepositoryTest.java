package com.md.ecommerce.inventoryservice.repository;

import com.md.ecommerce.inventoryservice.entity.PriceEntity;
import com.md.ecommerce.inventoryservice.entity.ProductEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

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

        ProductEntity productFound = productRepository.findByCode(productToSave.getCode());

        assertEquals(productToSave.getCode(), productFound.getCode());
        assertEquals(productToSave.getName(), productFound.getName());
        assertEquals(productToSave.getDescription(), productFound.getDescription());
        assertEquals(productToSave.getPriceEntity().getAmount(), productFound.getPriceEntity().getAmount());
    }

    private ProductEntity getStubProductEntity() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setCode("12345");
        productEntity.setName("testing product");
        productEntity.setDescription("a testing product");
        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setAmount(25.5);
        productEntity.setPriceEntity(priceEntity);

        return productEntity;
    }
}