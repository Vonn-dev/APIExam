package vonn.api.developer.exam.restcontroller;


import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import vonn.api.developer.exam.domain.Product;
import vonn.api.developer.exam.repository.ProductRepository;
import vonn.api.developer.exam.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1/product")
public class ProductRestController {

    private ProductService productService;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/all")
    public List<Product> getAllProducts() {
        List<Product> productsAlreadySave = productService.getAllProducts();
        if(productsAlreadySave.isEmpty()){
            List<Product> products = new ArrayList<>();
            return products;
        }
        return productsAlreadySave;
    }

    @GetMapping(value = "/{id}")
    public Product getProductById(@PathVariable("id") Integer productId) {
        Product productAlreadySave = productService.getProductById(productId).get();
        if(productAlreadySave == null){
            return new Product();
        }
        return productAlreadySave;
    }
    @PostMapping(value = "/new")
    public void saveProduct(@RequestBody Product product) {
        productService.saveProduct(product);
    }

    @PutMapping(value = "/update")
    public void updateProduct(@RequestBody Product product) {
        productService.saveProduct(product);
    }

    @DeleteMapping(value = "/deleteAll")
    public void deleteAllProducts() {
        productService.deleteAllProducts();
    }

    @DeleteMapping(value = "delete/{id}")
    public void deleteProductsById(@PathVariable("id") Integer productId) {
        productService.deleteProductById(productId);
    }


}
