package com.md.ecommerce.apiecommerce.controller;

import com.md.ecommerce.apiecommerce.TestUtils;
import com.md.ecommerce.apiecommerce.client.ClientServiceClient;
import com.md.ecommerce.commons.dto.Client;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ClientControllerTest {

    private static final String CLIENTS_RESOURCE_BASE_PATH = "/api/ecommerce/clients/";

    @Mock
    private ClientServiceClient clientServiceClient;

    @InjectMocks
    private ClientController clientController;

    MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    public void shouldGetAllClients() throws Exception {
        List<Client> clientList = new ArrayList<>();
        clientList.add(TestUtils.createTestClient("12345"));
        clientList.add(TestUtils.createTestClient("54321"));

        when(clientServiceClient.getAllClients()).thenReturn(clientList);

        mockMvc.perform(get(CLIENTS_RESOURCE_BASE_PATH))
                .andExpect(jsonPath("$.[0].id").value("12345"))
                .andExpect(jsonPath("$.[1].id").value("54321"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFindClientById() throws Exception {
        Client client = TestUtils.createTestClient("12345");

        when(clientServiceClient.findClientById(client.getId())).thenReturn(client);

        mockMvc.perform(get(CLIENTS_RESOURCE_BASE_PATH + client.getId()))
                .andExpect(jsonPath("$.id").value(client.getId()))
                .andExpect(jsonPath("$.firstName").value(client.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(client.getLastName()))
                .andExpect(jsonPath("$.address").value(client.getAddress()))
                .andExpect(jsonPath("$.phone").value(client.getPhone()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSaveClient() throws Exception {
        Client client = TestUtils.createTestClient("12345");

        when(clientServiceClient.saveClient(client)).thenReturn(client);

        mockMvc.perform(post(CLIENTS_RESOURCE_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"id\" : \"12345\",\n" +
                        "  \"firstName\" : \"john\",\n" +
                        "  \"lastName\" : \"wolf\",\n" +
                        "  \"address\" : \"fake address 123\",\n" +
                        "  \"phone\" : \"9876543\"\n" +
                        "}"))
                .andExpect(jsonPath("$.id").value(client.getId()))
                .andExpect(jsonPath("$.firstName").value(client.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(client.getLastName()))
                .andExpect(jsonPath("$.address").value(client.getAddress()))
                .andExpect(jsonPath("$.phone").value(client.getPhone()));
    }
}