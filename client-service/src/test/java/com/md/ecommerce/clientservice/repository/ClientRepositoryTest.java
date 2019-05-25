package com.md.ecommerce.clientservice.repository;

import com.md.ecommerce.clientservice.entity.ClientEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.md.ecommerce.clientservice.TestUtils.createTestClient;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void shouldSaveClient() {
        ClientEntity client = createTestClient("12345");

        clientRepository.save(client);

        Optional<ClientEntity> clientFound = clientRepository.findById(client.getId());

        assertEquals(Optional.of(client), clientFound);
    }

    @Test
    public void shouldRetrieveEmptyClientOptionalIfClientDoesNotExist() {
        Optional<ClientEntity> clientFound = clientRepository.findById("45678");

        assertFalse(clientFound.isPresent());
    }
}