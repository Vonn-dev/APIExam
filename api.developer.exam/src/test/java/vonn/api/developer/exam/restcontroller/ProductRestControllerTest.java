package vonn.api.developer.exam.restcontroller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vonn.api.developer.exam.domain.Product;
import vonn.api.developer.exam.service.ProductService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class ProductRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductService productService;

    private String asJsonString(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    @Order(1)
    public void get_all_products_through_endpoint() throws Exception {
        String response = mockMvc.perform(get("/v1/product/all"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Product> product = objectMapper.readValue(response, new TypeReference<>() {
        });
        Integer expected = 18;
        assertEquals(expected, product.size());
    }

    @Test
    @Order(2)
    public void get_product_by_id_through_endpoint() throws Exception {
        Product expectedProduct = new Product();
        expectedProduct.setId(1);
        expectedProduct.setProductName("Apple");
        expectedProduct.setProductDesc("Fresh Red Apple");
        expectedProduct.setProductType("FOOD");
        expectedProduct.setProductQuantity(100);
        expectedProduct.setUnitPrice(2);

        Integer productId = 1;
        String response = mockMvc.perform(get("/v1/product/{id}", productId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();


        Product product = objectMapper.readValue(response, Product.class);

        assertEquals(expectedProduct.getProductName(), product.getProductName());
        assertEquals(expectedProduct.getProductDesc(), product.getProductDesc());
        assertEquals(expectedProduct.getProductType(), product.getProductType());
        assertEquals(expectedProduct.getProductQuantity(), product.getProductQuantity());
        assertEquals(expectedProduct.getUnitPrice(), product.getUnitPrice());


    }

    @Test
    @Order(3)
    public void add_product_through_endpoint() throws Exception {
        Product newProduct = new Product();
        newProduct.setProductName("Smart Home Hub");
        newProduct.setProductDesc("A central hub for managing smart home devices");
        newProduct.setProductType("ELECTRONIC");
        newProduct.setProductQuantity(25);
        newProduct.setUnitPrice(99);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/product/new")
                        .content(objectMapper.writeValueAsString(newProduct))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());


        String response = mockMvc.perform(get("/v1/product/all"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Product> product = objectMapper.readValue(response, new TypeReference<>() {
        });
        Integer expected = 19;
        assertEquals(expected, product.size());


    }

    @Test
    @Order(4)
    public void update_product_through_endpoint() throws Exception {
        Product newProduct = new Product();
        newProduct.setId(3);
        newProduct.setProductName("Smart Home Hub");
        newProduct.setProductDesc("A central hub for managing smart home devices");
        newProduct.setProductType("ELECTRONIC");
        newProduct.setProductQuantity(25);
        newProduct.setUnitPrice(99);


        mockMvc.perform(MockMvcRequestBuilders.put("/v1/product/update")
                        .content(objectMapper.writeValueAsString(newProduct))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        String response = mockMvc.perform(get("/v1/product/{id}", 3))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Product product = objectMapper.readValue(response, new TypeReference<>() {
        });
        assertEquals(newProduct.getProductName(), product.getProductName());
        assertEquals(newProduct.getProductDesc(), product.getProductDesc());
        assertEquals(newProduct.getProductType(), product.getProductType());
        assertEquals(newProduct.getProductQuantity(), product.getProductQuantity());
        assertEquals(newProduct.getUnitPrice(), product.getUnitPrice());


    }

    @Test
    @Order(5)
    public void delete_product_by_id_through_endpoint() throws Exception {
        Integer productId = 2;
        mockMvc.perform(delete("/v1/product/delete/{id}", productId))
                .andExpect(status().isOk());

        String response = mockMvc.perform(get("/v1/product/all"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Product> product = objectMapper.readValue(response, new TypeReference<>() {
        });
        Integer expected = 18;

        assertEquals(expected, product.size());
    }

    @Test
    @Order(6)
    public void delete_all_products_through_endpoint() throws Exception {
        mockMvc.perform(delete("/v1/product/deleteAll"))
                .andExpect(status().isOk());

        String response = mockMvc.perform(get("/v1/product/all"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Product> product = objectMapper.readValue(response, new TypeReference<>() {
        });
        Integer expected = 18;
        assertNotEquals(expected, product.size());
    }
}
