package zalando.productsservice.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zalando.productsservice.dto.CreateProductDto;
import zalando.productsservice.exception.ProductNotFoundException;
import zalando.productsservice.model.Product;
import zalando.productsservice.rabbitmq.MessageWrapper;
import zalando.productsservice.rabbitmq.RabbitMQProducer;
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

    @Autowired
    private RabbitMQProducer rabbitMQProducer;


    public Product createProduct(CreateProductDto createProductDto){
        Product product = Product.builder()
                .brandId(createProductDto.getBrandId())
                .brandName(createProductDto.getBrandName())
                .name(createProductDto.getName())
                .price(createProductDto.getPrice())
                .material(createProductDto.getMaterial())
                .colors(createProductDto.getColors())
                .sizes(createProductDto.getSizes())
                .gender(createProductDto.getGender())
                .category(createProductDto.getCategory())
                .subcategory(createProductDto.getSubcategory())
                .build();

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        Product createdProduct = productRepository.save(product);


        for(CreateProductDto.ProductInventoryDto productInventoryDto : createProductDto.getInventory()){
            productInventoryDto.setProductId(createdProduct.getId());
        }

        rabbitMQProducer.bulkCreateInventoryItems(new MessageWrapper("bulkCreate", createProductDto.getInventory()));

        return createdProduct;
    }


    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(String id){
        return productRepository.findById(id).get();
    }

    public Product updateProduct(String id, CreateProductDto createProductDto) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product updatedProduct = existingProduct.get();
            ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
            try {
                mapper.updateValue(updatedProduct, createProductDto);
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
