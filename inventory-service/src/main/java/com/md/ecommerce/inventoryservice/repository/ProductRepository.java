package com.md.ecommerce.inventoryservice.repository;

import com.md.ecommerce.inventoryservice.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Double> {

    Optional<ProductEntity> findByCode(String code);
}
