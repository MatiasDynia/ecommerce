package com.md.ecommerce.apiecommerce.client;

import com.md.ecommerce.commons.dto.Product;
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

import static com.md.ecommerce.apiecommerce.client.InventoryServiceClientImpl.BASE_INVENTORY_PRODUCTS_SERVICE_URL;
import static com.md.ecommerce.apiecommerce.client.InventoryServiceClientImpl.INVENTORY_SERVICE_HOST;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
@RunWith(SpringRunner.class)
public class InventoryServiceClientImplTest {

    private RestTemplate restTemplate;
    private InventoryServiceClientImpl inventoryServiceClient;

    @Before
    public void setup() {
        restTemplate = new RestTemplate();
        inventoryServiceClient = new InventoryServiceClientImpl(restTemplate);
    }

    @Test
    public void shouldGetAllProducts() {

        String productListJson = "[\n" +
                "    {\n" +
                "        \"code\": \"12\",\n" +
                "        \"name\": \"product 12\",\n" +
                "        \"description\": \"a product 12\",\n" +
                "        \"price\": {\n" +
                "            \"amount\": 65.4\n" +
                "        },\n" +
                "        \"stock\": 24\n" +
                "    },\n" +
                "    {\n" +
                "        \"code\": \"24\",\n" +
                "        \"name\": \"product 24\",\n" +
                "        \"description\": \"a product 24\",\n" +
                "        \"price\": {\n" +
                "            \"amount\": 52.4\n" +
                "        },\n" +
                "        \"stock\": 5\n" +
                "    }\n" +
                "]";

        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        server.expect(requestTo(INVENTORY_SERVICE_HOST + BASE_INVENTORY_PRODUCTS_SERVICE_URL))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(productListJson, MediaType.APPLICATION_JSON));

        List<Product> productList = inventoryServiceClient.getAllProducts();

        BDDAssertions.then(productList.get(0).getCode()).isEqualTo("12");
        BDDAssertions.then(productList.get(0).getName()).isEqualTo("product 12");
        BDDAssertions.then(productList.get(0).getDescription()).isEqualTo("a product 12");
        BDDAssertions.then(productList.get(0).getPrice().getAmount()).isEqualTo(65.4);
        BDDAssertions.then(productList.get(0).getStock()).isEqualTo(24);
        BDDAssertions.then(productList.get(1).getCode()).isEqualTo("24");
        BDDAssertions.then(productList.get(1).getName()).isEqualTo("product 24");
        BDDAssertions.then(productList.get(1).getDescription()).isEqualTo("a product 24");
        BDDAssertions.then(productList.get(1).getPrice().getAmount()).isEqualTo(52.4);
        BDDAssertions.then(productList.get(1).getStock()).isEqualTo(5);
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

        server.expect(requestTo(INVENTORY_SERVICE_HOST + BASE_INVENTORY_PRODUCTS_SERVICE_URL + "54"))
                .andExpect(method(HttpMethod.GET))
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

        String productToSaveJson = "{\n" +
                "\"code\" : \"24\",\n" +
                "\"name\" : \"product 24\",\n" +
                "\"description\" : \"a product 24\",\n" +
                "\"price\" : {\n" +
                "\t\"amount\" : 52.4\n" +
                "},\n" +
                "\"stock\" : 5\n" +
                "}";

        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        server.expect(requestTo(INVENTORY_SERVICE_HOST + BASE_INVENTORY_PRODUCTS_SERVICE_URL))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(productToSaveJson, MediaType.APPLICATION_JSON));

        Product productSaved = inventoryServiceClient.saveProduct(any(Product.class));

        BDDAssertions.then(productSaved.getCode()).isEqualTo("24");
    }
}