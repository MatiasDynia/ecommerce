package com.md.ecommerce.clientservice.exceptions;

import com.md.ecommerce.clientservice.repository.ClientRepository;
import com.md.ecommerce.clientservice.service.ClientServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ClientNotFoundExceptionTest {

    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private ClientRepository clientRepository;

    @Test(expected = ClientNotFoundException.class)
    public void shouldThrowClientNotFoundException() {
        when(clientRepository.findById(anyString())).thenReturn(Optional.empty());

        clientService.findClientById(anyString());
    }
}