package com.md.ecommerce.shoppingservice.repository;

import com.md.ecommerce.shoppingservice.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, String> {

}
