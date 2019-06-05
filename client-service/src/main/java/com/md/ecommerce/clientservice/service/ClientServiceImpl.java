package com.md.ecommerce.clientservice.service;

import com.md.ecommerce.clientservice.entity.ClientEntity;
import com.md.ecommerce.clientservice.exceptions.ClientNotFoundException;
import com.md.ecommerce.clientservice.repository.ClientRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    @HystrixCommand
    public ClientEntity findClientById(String id) {
        log.info("Retrieving client " + id + "...");

        Optional<ClientEntity> client = clientRepository.findById(id);

        if(client.isPresent()) {
            log.info("Client " + id + " found!");

            return client.get();
        } else {
            log.error("Client " + id + " not found!");

            throw new ClientNotFoundException("Client " + id + " not found!");
        }
    }

    @Override
    @HystrixCommand
    public Iterable<ClientEntity> findAll() {
        log.info("Retrieving all clients...");

        return clientRepository.findAll();
    }

    @Override
    @HystrixCommand
    public ClientEntity save(ClientEntity client) {
        log.info("Saving order...");

        ClientEntity clientSaved = clientRepository.save(client);

        log.info("Client " + clientSaved.getId() + " saved successfully!");

        return clientSaved;
    }
}