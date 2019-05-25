package com.md.ecommerce.clientservice.service;

import com.md.ecommerce.clientservice.entity.ClientEntity;

public interface ClientService {

    ClientEntity findClientById(String id);
    Iterable<ClientEntity> findAll();
    ClientEntity save(ClientEntity client);
}