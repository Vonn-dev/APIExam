package vonn.api.developer.exam.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vonn.api.developer.exam.domain.Product;

@Repository("ProductRepository")
public interface ProductRepository extends CrudRepository<Product, Integer> {
}
