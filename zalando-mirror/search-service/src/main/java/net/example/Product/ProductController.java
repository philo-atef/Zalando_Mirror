package net.example.Product;

//public class ProductController {
//}

import net.example.Product.Product;
import net.example.Product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController

public class ProductController {
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    //private ProductService productService;
    private ProductRepository productRepository;


    @GetMapping("/getAllProducts")
    public List<Product> getAllUser(){
        return productRepository.findAll();
    }
    @PostMapping("/addProduct")
    public Product addUser(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @GetMapping("/searchproducts")
    public List<Product> search (@RequestParam(defaultValue = "") String searchQuery,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "3") int size)
    {
if(searchQuery.length()==0)
    return new ArrayList<>();
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matching(searchQuery);
        TextQuery query = TextQuery.queryText(criteria);
        Pageable paging = PageRequest.of(page, size);
        query.with(paging);
        List<Product> results = mongoTemplate.find(query, Product.class);
        return results;
    }
    @GetMapping("/brandproducts/")
    public List<Product> getbrand(@RequestParam(defaultValue = "") String searchQuery,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "3") int size) {
        if(searchQuery.length()==0)
            return new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Query query = new Query();
        query.addCriteria(Criteria.where("brandName").is(searchQuery));
        query.with(paging);
        List<Product> results = mongoTemplate.find(query,Product.class);
        return results;
    }
    @GetMapping("/categoryproducts")
    public List<Product> getcategory(@RequestParam(defaultValue = "") String searchQuery,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "3") int size) {
        if(searchQuery.length()==0)
            return new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Query query = new Query();
        query.addCriteria(Criteria.where("category").is(searchQuery));
        query.with(paging);
        List<Product> results = mongoTemplate.find(query, Product.class);
        return results;
    }


    @GetMapping("/filterproducts")
    public List<Product> filterproducts(@RequestParam(defaultValue = "") String category,
                                     @RequestParam(defaultValue = "") String value,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "3") int size
                                  ) {
        if(category.length()==0)
            return new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        String[] categories= category.split(",");
        String[] values= value.split(",");
        Query query = new Query();
        Criteria[] subCat = new Criteria[categories.length];
        for (int i=0;i<categories.length;i++){

            String[] subValues= values[i].split("/");
//            for (int j=0;i<subValues.length;i++)
//
//                query.addCriteria(Criteria.where(categories[i]).is(subValues[j]));
            Criteria[] subCriterias = new Criteria[subValues.length];
            for (int j = 0; j < subValues.length; j++) {
                subCriterias[j] = Criteria.where(categories[i]).is(subValues[j]);
            }
            subCat[i]=new Criteria().orOperator(subCriterias);
               }
        query.addCriteria(new Criteria().andOperator(subCat));
        query.with(paging);
        List<Product> results = mongoTemplate.find(query, Product.class);
        return results;
    }

}