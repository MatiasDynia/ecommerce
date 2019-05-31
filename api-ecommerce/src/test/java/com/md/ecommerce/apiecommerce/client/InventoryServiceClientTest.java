package com.md.ecommerce.apiecommerce.client;

import com.md.ecommerce.commons.dto.Product;
import org.assertj.core.api.BDDAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
@RunWith(SpringRunner.class)
public class InventoryServiceClientTest {

    private RestTemplate restTemplate;
    private InventoryServiceClient inventoryServiceClient;

    @Before
    public void setup() {
        restTemplate = new RestTemplate();
        inventoryServiceClient = new InventoryServiceClient(restTemplate);
    }

    @Test
    public void shouldGetAllProducts() {

    }

    @Test
    public void shouldGetProductByCode() {

        String productJson = "{\n" +
                "    \"code\": \"54\",\n" +
                "    \"name\": \"product54\",\n" +
                "    \"description\": \"some product54\",\n" +
                "    \"price\": {\n" +
                "        \"amount\": 1543.23\n" +
                "    },\n" +
                "    \"stock\": 10\n" +
                "}";

        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        server.expect(requestTo("http://localhost:9001/api/inventory/products/54"))
                .andRespond(withSuccess(productJson, MediaType.APPLICATION_JSON));

        Product product = inventoryServiceClient.getProductByCode("54");

        BDDAssertions.then(product.getCode()).isEqualTo("54");
        BDDAssertions.then(product.getName()).isEqualTo("product54");
        BDDAssertions.then(product.getDescription()).isEqualTo("some product54");
        BDDAssertions.then(product.getPrice().getAmount()).isEqualTo(1543.23);
        BDDAssertions.then(product.getStock()).isEqualTo(10);
    }

    @Test
    public void shouldSaveProduct() {
    }
}