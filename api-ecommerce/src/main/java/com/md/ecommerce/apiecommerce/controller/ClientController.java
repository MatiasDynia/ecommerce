package com.md.ecommerce.apiecommerce.controller;

import com.md.ecommerce.apiecommerce.client.ClientServiceClient;
import com.md.ecommerce.commons.dto.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ecommerce/clients")
public class ClientController {

    private ClientServiceClient clientServiceClient;

    @Autowired
    public ClientController(ClientServiceClient clientServiceClient) {
        this.clientServiceClient = clientServiceClient;
    }

    @GetMapping("")
    public List<Client> getAllClients() {
        return clientServiceClient.getAllClients();
    }

    @GetMapping("/{clientId}")
    public Client findClientById(@PathVariable String clientId) {
        return clientServiceClient.findClientById(clientId);
    }

    @PostMapping
    public Client saveClient(@RequestBody Client client) {
        return clientServiceClient.saveClient(client);
    }
}
