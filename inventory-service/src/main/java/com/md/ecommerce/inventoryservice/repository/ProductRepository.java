package com.md.ecommerce.inventoryservice.repository;

import com.md.ecommerce.inventoryservice.domain.Product;
import com.md.ecommerce.inventoryservice.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Double> {

    ProductEntity findByCode(String code);
    ProductEntity save(Product product);
    List<ProductEntity> findAll();
}
