package com.md.ecommerce.apiecommerce.client;

import com.md.ecommerce.commons.dto.Client;

import java.util.List;

public interface ClientServiceClient {

    public List<Client> getAllClients();
    public Client findClientById(String id);
    public Client saveClient(Client client);
}
