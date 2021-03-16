package com.example.gorski.controllers;

import com.example.gorski.domain.users.*;
import com.example.gorski.messages.request.EditUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Iterable<User> getUsers() {
        return userRepository.findAll();
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
