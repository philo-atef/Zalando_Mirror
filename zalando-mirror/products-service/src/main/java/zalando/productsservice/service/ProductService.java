package zalando.productsservice.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import zalando.productsservice.dto.ProductRequest;
import zalando.productsservice.exception.ProductNotFoundException;
import zalando.productsservice.model.Product;
import zalando.productsservice.repository.ProductRepository;


import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final Validator validator;

    public Product createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .brandId(productRequest.getBrandId())
                .brandName(productRequest.getBrandName())
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .material(productRequest.getMaterial())
                .colors(productRequest.getColors())
                .sizes(productRequest.getSizes())
                .gender(productRequest.getGender())
                .category(productRequest.getCategory())
                .subcategory(productRequest.getSubcategory())
                .build();

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        return productRepository.save(product);
    }


    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(String id){
        return productRepository.findById(id).get();
    }

    public Product updateProduct(String id, ProductRequest productRequest) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product updatedProduct = existingProduct.get();
            ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
            try {
                mapper.updateValue(updatedProduct, productRequest);
                return productRepository.save(updatedProduct);
            } catch (JsonMappingException e) {
                throw new IllegalArgumentException("Invalid product request", e);
            }
        } else {
            throw new ProductNotFoundException("No Product found with id: " + id);
        }
    }


    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}
