package com.example.gorski.controllers;

import com.example.gorski.domain.products.Product;
import com.example.gorski.domain.products.ProductRepository;
import com.example.gorski.messages.request.ProductAdd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/app")
public class ProductsController {
    private final ProductRepository productRepository;

    @Autowired
    public ProductsController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Iterable<Product> getProducts() {
        return productRepository.findAll();
    }

    @PostMapping("/products")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    void addProduct(@RequestBody ProductAdd addRequest) {
        BigDecimal properPrice = new BigDecimal(addRequest.getPrice());

        Product product = new Product(addRequest.getName(), properPrice, addRequest.getProductGender(), addRequest.getSeason(), addRequest.getCategory(), addRequest.getPictureName(), addRequest.getLink());
        productRepository.save(product);
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
