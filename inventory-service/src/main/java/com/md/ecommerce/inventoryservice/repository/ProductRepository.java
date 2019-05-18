package com.md.ecommerce.inventoryservice.repository;

import com.md.ecommerce.inventoryservice.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Double> {

    ProductEntity findByCode(String code);
}
