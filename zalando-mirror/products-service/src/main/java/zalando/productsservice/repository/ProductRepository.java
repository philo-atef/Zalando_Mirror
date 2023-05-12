package zalando.productsservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import zalando.productsservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}
