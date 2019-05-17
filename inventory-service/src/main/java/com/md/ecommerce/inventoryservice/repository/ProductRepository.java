package com.md.ecommerce.inventoryservice.repository;

import com.md.ecommerce.inventoryservice.domain.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Double> {

    Product findByCode(String code);
    Product save(Product product);
    List<Product> findAll();
}
