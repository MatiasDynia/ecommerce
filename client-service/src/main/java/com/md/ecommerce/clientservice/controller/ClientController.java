package com.md.ecommerce.clientservice.controller;

import com.md.ecommerce.clientservice.entity.ClientEntity;
import com.md.ecommerce.clientservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/client/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("")
    public Iterable<ClientEntity> findAll() {
        return clientService.findAll();
    }

    @GetMapping("/{id}")
    public ClientEntity findClientById(@PathVariable ("id") String id) {
        return clientService.findClientById(id);
    }

    @PostMapping
    public ClientEntity save(@RequestBody ClientEntity client) {
        return clientService.save(client);
    }
}