package com.md.ecommerce.clientservice.repository;

import com.md.ecommerce.clientservice.entity.ClientEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<ClientEntity, String> {

}