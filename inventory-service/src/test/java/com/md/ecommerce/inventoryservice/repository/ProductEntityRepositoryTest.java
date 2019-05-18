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
        assertEquals(productToSave.getPrice().getAmount(), productFound.getPrice().getAmount());
        assertEquals(productToSave.getStock(), productFound.getStock());
    }

    private ProductEntity getStubProductEntity() {
        ProductEntity product = new ProductEntity();
        product.setCode("12345");
        product.setName("testing product");
        product.setDescription("a testing product");
        product.setStock(12);
        PriceEntity price = new PriceEntity();
        price.setAmount(25.5);
        product.setPrice(price);

        return product;
    }
}