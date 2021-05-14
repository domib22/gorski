package com.example.gorski.controllers;

import com.example.gorski.domain.products.ProductRepository;
import com.example.gorski.domain.products.model.Product;
import com.example.gorski.domain.users.*;
import com.example.gorski.domain.users.model.User;
import com.example.gorski.messages.request.EditUser;
import com.example.gorski.messages.response.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/app")
public class UsersController {
    private final UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/ownedProducts")
    @ResponseBody
    public Iterable<Product> getUserOwnedProducts(String username) {
            User user = userRepository.findByUserName(username);

            return user.getOwnedProducts();
    }

    @PutMapping("/users")
    public ResponseEntity<?> addUserOwnedProduct(String username, Long idProduct) {
        User user = userRepository.findByUserName(username);
        Product product = productRepository.getOne(idProduct);
        Set<Product> ownedProducts = user.getOwnedProducts();

        if(ownedProducts.contains(product)) {
            return new ResponseEntity<>(new Message("The product is already included"), HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            ownedProducts.add(product);
            user.setOwnedProducts(ownedProducts);
            userRepository.save(user);
            return new ResponseEntity<>(new Message("The product add successfully to user-owned products"), HttpStatus.OK);
        }
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    void updateUserData(@RequestBody EditUser editRequest) {
        if (!editRequest.getUsername().isBlank()) {
            if (userRepository.existsById(editRequest.getId())) {
                User user = userRepository.getOne(editRequest.getId());
                user.setUserName(editRequest.getUsername());
                user.setUserGender(editRequest.getUserGender());

                userRepository.save(user);
            }
        }
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

}
