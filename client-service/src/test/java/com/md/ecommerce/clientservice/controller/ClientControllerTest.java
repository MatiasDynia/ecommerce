package com.md.ecommerce.clientservice.controller;

import com.md.ecommerce.clientservice.entity.ClientEntity;
import com.md.ecommerce.clientservice.service.ClientService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.md.ecommerce.clientservice.TestUtils.createTestClient;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class ClientControllerTest {

    @InjectMocks
    private ClientController clientController;

    @Mock
    private ClientService clientService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    public void shouldFindClientById() throws Exception {
        ClientEntity client = createTestClient("12345");

        when(clientService.findClientById("12345")).thenReturn(client);

        mockMvc.perform(get("/api/client/clients/" + client.getId()))
                .andExpect(jsonPath("$.id").value(client.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFindAllClients() throws Exception {
        List<ClientEntity> clients = new ArrayList<>();
        clients.add(createTestClient("12345"));
        clients.add(createTestClient("45678"));
        clients.add(createTestClient("67890"));

        when(clientService.findAll()).thenReturn(clients);

        mockMvc.perform(get("/api/client/clients"))
                .andExpect(jsonPath("$.[0].id").value(clients.get(0).getId()))
                .andExpect(jsonPath("$.[1].id").value(clients.get(1).getId()))
                .andExpect(jsonPath("$.[2].id").value(clients.get(2).getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSaveClient() throws Exception {
        ClientEntity client = createTestClient("12345");

        when(clientService.save(client)).thenReturn(client);

        mockMvc.perform(post("/api/client/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"id\" : \"12345\",\n" +
                        "  \"firstName\" : \"john\",\n" +
                        "  \"lastName\" : \"wolf\",\n" +
                        "  \"address\" : \"fake address 123\",\n" +
                        "  \"phone\" : \"9876543\",\n" +
                        "  \"mail\" : \"test@mail.com\"\n" +
                        "}"))
                .andExpect(jsonPath("$.id").value(client.getId()))
                .andExpect(jsonPath("$.firstName").value(client.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(client.getLastName()))
                .andExpect(jsonPath("$.address").value(client.getAddress()))
                .andExpect(jsonPath("$.phone").value(client.getPhone()));
    }
}