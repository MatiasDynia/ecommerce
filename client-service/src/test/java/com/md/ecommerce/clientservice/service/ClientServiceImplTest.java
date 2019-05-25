package com.md.ecommerce.clientservice.service;

import com.md.ecommerce.clientservice.entity.ClientEntity;
import com.md.ecommerce.clientservice.repository.ClientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.md.ecommerce.clientservice.TestUtils.createTestClient;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ClientServiceImplTest {

    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private ClientRepository clientRepository;

    @Test
    public void shouldRetrieveClientById() {
        ClientEntity client = createTestClient("12345");

        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        ClientEntity clientFound = clientService.findClientById(client.getId());

        assertEquals(client, clientFound);
        verify(clientRepository).findById(client.getId());
    }

    @Test
    public void shouldFindAllClients() {
        List<ClientEntity> clients = new ArrayList<>();
        clients.add(createTestClient("12345"));
        clients.add(createTestClient("45678"));
        clients.add(createTestClient("67890"));

        when(clientRepository.findAll()).thenReturn(clients);

        Iterable<ClientEntity> clientsFound = clientService.findAll();

        assertEquals(clients, clientsFound);
        verify(clientRepository).findAll();
    }

    @Test
    public void shouldSaveClient() {
        ClientEntity client = createTestClient("12345");

        when(clientRepository.save(client)).thenReturn(client);

        ClientEntity clientSaved = clientService.save(client);

        assertEquals(client, clientSaved);
        verify(clientRepository).save(client);
    }
}