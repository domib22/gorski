package com.example.gorski.controllers;

import com.example.gorski.domain.User;
import com.example.gorski.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UsersController {

    private final UserRepository userRepository;

    @Autowired
    public UsersController(UserRepository userRepository) { this.userRepository = userRepository; }

    @GetMapping("/users")
    public Iterable<User> getUsers() { return userRepository.findAll(); }

    @PostMapping("/users")
    public ResponseEntity addUser(@RequestBody User user) {
        Optional<User> userFromDb = userRepository.findByUserName(user.getUserName());

        if (!userFromDb.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/users")
    void updateUserData(@RequestBody User user) {
        userRepository.save(user);
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

}
