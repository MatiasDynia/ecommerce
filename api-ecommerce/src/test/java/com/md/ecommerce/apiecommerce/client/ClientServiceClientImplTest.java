package com.md.ecommerce.apiecommerce.client;

import com.md.ecommerce.commons.dto.Client;
import org.assertj.core.api.BDDAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.md.ecommerce.apiecommerce.client.ClientServiceClientImpl.BASE_CLIENT_SERVICE_URL;
import static com.md.ecommerce.apiecommerce.client.ClientServiceClientImpl.CLIENT_SERVICE_HOST;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ClientServiceClientImplTest {

    private RestTemplate restTemplate;
    private ClientServiceClientImpl clientServiceClient;

    @Before
    public void setup() {
        restTemplate = new RestTemplate();
        clientServiceClient = new ClientServiceClientImpl(restTemplate);
    }

    @Test
    public void ShouldGetAllClients() {

        String clientListJson = "[{\n" +
                "    \"id\": \"12345\",\n" +
                "    \"firstName\": \"John\",\n" +
                "    \"lastName\": \"Doe\",\n" +
                "    \"address\": \"fake address 123\",\n" +
                "    \"phone\": \"123456789\"\n" +
                "},{\n" +
                "    \"id\": \"56789\",\n" +
                "    \"firstName\": \"Jane\",\n" +
                "    \"lastName\": \"Doe\",\n" +
                "    \"address\": \"fake address 567\",\n" +
                "    \"phone\": \"987654321\"\n" +
                "}]";

        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        server.expect(requestTo(CLIENT_SERVICE_HOST + BASE_CLIENT_SERVICE_URL))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(clientListJson, MediaType.APPLICATION_JSON));

        List<Client> clientsFound = clientServiceClient.getAllClients();

        Client firstClient = clientsFound.get(0);
        Client secondClient = clientsFound.get(1);

        BDDAssertions.then(firstClient.getId()).isEqualTo("12345");
        BDDAssertions.then(firstClient.getFirstName()).isEqualTo("John");
        BDDAssertions.then(firstClient.getLastName()).isEqualTo("Doe");
        BDDAssertions.then(firstClient.getAddress()).isEqualTo("fake address 123");
        BDDAssertions.then(firstClient.getPhone()).isEqualTo("123456789");

        BDDAssertions.then(secondClient.getId()).isEqualTo("56789");
        BDDAssertions.then(secondClient.getFirstName()).isEqualTo("Jane");
        BDDAssertions.then(secondClient.getLastName()).isEqualTo("Doe");
        BDDAssertions.then(secondClient.getAddress()).isEqualTo("fake address 567");
        BDDAssertions.then(secondClient.getPhone()).isEqualTo("987654321");
    }

    @Test
    public void shouldFindClientById() {

        String clientJson = "{\n" +
                "            \"id\": \"12345\",\n" +
                "            \"firstName\": \"John\",\n" +
                "            \"lastName\": \"Doe\",\n" +
                "            \"address\": \"fake address 123\",\n" +
                "            \"phone\": \"123456789\"\n" +
                "            }";

        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        server.expect(requestTo(CLIENT_SERVICE_HOST + BASE_CLIENT_SERVICE_URL + "12345"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(clientJson, MediaType.APPLICATION_JSON));

        Client clientFound = clientServiceClient.findClientById("12345");

        BDDAssertions.then(clientFound.getId()).isEqualTo("12345");
        BDDAssertions.then(clientFound.getFirstName()).isEqualTo("John");
        BDDAssertions.then(clientFound.getLastName()).isEqualTo("Doe");
        BDDAssertions.then(clientFound.getAddress()).isEqualTo("fake address 123");
        BDDAssertions.then(clientFound.getPhone()).isEqualTo("123456789");
    }

    @Test
    public void shouldSaveClient() {

        String clientToSaveJson = "{\n" +
                "            \"id\": \"12345\",\n" +
                "            \"firstName\": \"John\",\n" +
                "            \"lastName\": \"Doe\",\n" +
                "            \"address\": \"fake address 123\",\n" +
                "            \"phone\": \"123456789\"\n" +
                "            }";

        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        server.expect(requestTo(CLIENT_SERVICE_HOST + BASE_CLIENT_SERVICE_URL))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(clientToSaveJson, MediaType.APPLICATION_JSON));

        Client clientSaved = clientServiceClient.saveClient(any(Client.class));

        BDDAssertions.then(clientSaved.getId()).isEqualTo("12345");
        BDDAssertions.then(clientSaved.getFirstName()).isEqualTo("John");
        BDDAssertions.then(clientSaved.getLastName()).isEqualTo("Doe");
        BDDAssertions.then(clientSaved.getAddress()).isEqualTo("fake address 123");
        BDDAssertions.then(clientSaved.getPhone()).isEqualTo("123456789");
    }
}