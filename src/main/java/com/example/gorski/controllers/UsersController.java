package com.example.gorski.controllers;

import com.example.gorski.domain.users.User;
import com.example.gorski.domain.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/app")
public class UsersController {
    private final UserRepository userRepository;

    @Autowired
    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @PutMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    void updateUserData(@RequestBody User user) {
        userRepository.save(user);
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

}
