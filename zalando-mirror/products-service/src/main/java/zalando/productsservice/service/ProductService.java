package zalando.productsservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shared.dto.inventory.CreateInventoryItemRequest;
import com.shared.dto.inventory.InventoryItemResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import zalando.productsservice.dto.CreateProductDto;
import zalando.productsservice.exception.ProductNotFoundException;
import zalando.productsservice.model.Product;
import zalando.productsservice.rabbitmq.MessageWrapper;
import zalando.productsservice.rabbitmq.RabbitMQProducer;
import zalando.productsservice.repository.ProductRepository;


import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final Validator validator;
    private final AmazonS3 amazonS3Client;

    @Autowired
    private RabbitMQProducer rabbitMQProducer;


    public Product createProduct(CreateProductDto createProductDto, MultipartFile file){
        System.out.println(file);
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

        try{
            String fileUrl = uploadFileToS3(file);
            System.out.println(fileUrl);
        }catch(IOException ex){
            System.out.println(ex);
        }

        Product createdProduct = productRepository.save(product);


        for(CreateProductDto.ProductInventoryDto productInventoryDto : createProductDto.getInventory()){
            productInventoryDto.setProductId(createdProduct.getId());
        }

        List<CreateInventoryItemRequest> request =  createProductDto.getInventory().stream()
                .map(item -> CreateInventoryItemRequest.builder()
                        .productId(item.getProductId())
                        .color(item.getColor())
                        .size(item.getSize())
                        .quantity(item.getQuantity())
                        .build())
                .collect(Collectors.toList());

        List<InventoryItemResponse> response = rabbitMQProducer.bulkCreateInventoryItems(request);
        log.info(String.format("Inventory Service: Created Inventory items => %s", response.toString()));

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


    private String uploadFileToS3(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString(); // Generate a unique file name

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        amazonS3Client.putObject("image-dump-app", fileName, file.getInputStream(), metadata);

        return amazonS3Client.getUrl("image-dump-app", fileName).toString();
    }
}
