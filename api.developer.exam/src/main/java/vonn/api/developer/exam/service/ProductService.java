package vonn.api.developer.exam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vonn.api.developer.exam.domain.Product;
import vonn.api.developer.exam.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service("ProductService")
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
        return (List<Product>) productRepository.findAll();
    }

    public Optional<Product> getProductById(Integer productId){
        return productRepository.findById(productId);
    }

    public void deleteAllProducts(){
        productRepository.deleteAll();
    }

    public void deleteProductById(Integer productId){
        productRepository.deleteById(productId);
    }

    public void saveProduct(Product product){
        productRepository.save(product);
    }

}
