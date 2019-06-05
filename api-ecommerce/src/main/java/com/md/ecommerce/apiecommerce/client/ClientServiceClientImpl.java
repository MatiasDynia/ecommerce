package com.md.ecommerce.apiecommerce.client;

import com.md.ecommerce.commons.dto.Client;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
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
    @HystrixCommand
    public List<Client> getAllClients() {
        log.info("Getting all clients...");

        ResponseEntity<List<Client>> responseEntity = restTemplate
                .exchange(CLIENT_SERVICE_HOST + BASE_CLIENT_SERVICE_URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Client>>() {
                        });

        return responseEntity.getBody();
    }

    @Override
    @HystrixCommand
    public Client findClientById(String id) {
        log.info("Finding client " + id + "...");

        return restTemplate.getForObject(CLIENT_SERVICE_HOST + BASE_CLIENT_SERVICE_URL + "{id}",
                Client.class,
                id);
    }

    @Override
    @HystrixCommand
    public Client saveClient(Client client) {
        log.info("Creating new client...");

        return restTemplate.postForObject(CLIENT_SERVICE_HOST + BASE_CLIENT_SERVICE_URL,
                client,
                Client.class);
    }
}
