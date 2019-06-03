package com.md.ecommerce.apiecommerce.client;

import com.md.ecommerce.commons.dto.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ClientServiceClientImpl implements ClientServiceClient {

    static final String CLIENT_SERVICE_HOST = "http://client-service";
    static final String BASE_CLIENT_SERVICE_URL = "/api/client/clients/";

    private final RestTemplate restTemplate;

    @Autowired
    public ClientServiceClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Client> getAllClients() {
        ResponseEntity<List<Client>> responseEntity = restTemplate
                .exchange(CLIENT_SERVICE_HOST + BASE_CLIENT_SERVICE_URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Client>>() {
                        });

        return responseEntity.getBody();
    }

    @Override
    public Client findClientById(String id) {
        return restTemplate.getForObject(CLIENT_SERVICE_HOST + BASE_CLIENT_SERVICE_URL + "{id}",
                Client.class,
                id);
    }

    @Override
    public Client saveClient(Client client) {
        return restTemplate.postForObject(CLIENT_SERVICE_HOST + BASE_CLIENT_SERVICE_URL,
                client,
                Client.class);
    }
}
