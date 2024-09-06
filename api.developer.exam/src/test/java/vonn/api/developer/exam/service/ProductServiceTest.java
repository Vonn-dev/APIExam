package vonn.api.developer.exam.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vonn.api.developer.exam.domain.Product;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    @Order(1)
    void get_all_products() {
        Integer expected = 18;
        Assertions.assertEquals(expected, productService.getAllProducts().size());
    }

    @Test
    @Order(2)
    void get_product_by_id() {
        Assertions.assertNotNull(productService.getProductById(1));
    }

    @Test
    @Order(3)
    void save_product() {
        Product newProduct = new Product();
        newProduct.setProductName("Smart Home Hub");
        newProduct.setProductDesc("A central hub for managing smart home devices");
        newProduct.setProductType("ELECTRONIC");
        newProduct.setProductQuantity(25);
        newProduct.setUnitPrice(99);
        productService.saveProduct(newProduct);


        Integer expected = 19;
        Assertions.assertEquals(expected, productService.getAllProducts().size());
    }

    @Test
    @Order(4)
    void update_product() {
        Product newProduct = new Product();
        newProduct.setId(1);
        newProduct.setProductName("Smart Home Hub");
        newProduct.setProductDesc("A central hub for managing smart home devices");
        newProduct.setProductType("ELECTRONIC");
        newProduct.setProductQuantity(25);
        newProduct.setUnitPrice(99);
        productService.saveProduct(newProduct);

        Product productAlreadySaved = new Product();
        productAlreadySaved.setId(1);
        productAlreadySaved.setProductName("Apple");
        productAlreadySaved.setProductDesc("Fresh Red Apple");
        productAlreadySaved.setProductType("FOOD");
        productAlreadySaved.setProductQuantity(100);
        productAlreadySaved.setUnitPrice(2);

        Integer expected = 19;
        Assertions.assertEquals(expected, productService.getAllProducts().size());
        Assertions.assertNotEquals(productAlreadySaved, productService.getProductById(1).get());
    }

    @Test
    @Order(5)
    void delete_product_by_id() {
        boolean isProductId2Deleted = true;
        productService.deleteProductById(2);
        Assertions.assertEquals(isProductId2Deleted, productService.getProductById(2).isEmpty());
    }

    @Test
    @Order(6)
    void delete_all_products() {
        Integer expected = 0;
        productService.deleteAllProducts();
        Assertions.assertEquals(0, productService.getAllProducts().size());
    }







}
