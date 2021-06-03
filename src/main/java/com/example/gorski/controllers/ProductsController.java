package com.example.gorski.controllers;

import com.example.gorski.domain.products.ReviewRepository;
import com.example.gorski.domain.products.model.Product;
import com.example.gorski.domain.products.model.Review;
import com.example.gorski.domain.products.search.ProductPredicatesBuilder;
import com.example.gorski.domain.products.ProductRepository;
import com.example.gorski.messages.request.ProductAdd;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/app")
public class ProductsController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    public ProductsController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    public Iterable<Product> searchProducts(@RequestParam(value = "search") String search) {
        if(search.isBlank()) {
            return productRepository.findAll();
        }

        ProductPredicatesBuilder builder = new ProductPredicatesBuilder();

        Pattern pattern = Pattern.compile("(\\w+?)([:<>])(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        BooleanExpression exp = builder.build();
        return productRepository.findAll(exp);
    }

    @GetMapping("/products/{id}")
    public Iterable<Review> getProductReviews(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Product with id=" + id + " not found"));

        return product.getReviews();
    }

    @PostMapping("/products/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    void addReview(@PathVariable Long id, Integer stars, String opinion, String userName) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Product with id=" + id + " not found"));

        Review newReview = new Review(stars, opinion, userName, product);
        reviewRepository.save(newReview);
    }

    @PostMapping("/products")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> addProduct(@RequestBody ProductAdd addRequest) {
        BigDecimal properPrice = new BigDecimal(addRequest.getPrice());

        try {
            Product product = new Product(addRequest.getName(), properPrice, addRequest.getProductGender(), addRequest.getSeason(), addRequest.getCategory(), addRequest.getPictureName(), addRequest.getLink(), addRequest.getDescription());
            productRepository.save(product);
            return ResponseEntity.ok(product);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/products")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    void updateProduct(@RequestBody Product product) {
        productRepository.save(product);
    }

    @DeleteMapping("/products/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }

}
