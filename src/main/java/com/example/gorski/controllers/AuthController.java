package com.example.gorski.controllers;

import com.example.gorski.domain.users.*;
import com.example.gorski.messages.request.Login;
import com.example.gorski.messages.request.Registration;
import com.example.gorski.messages.response.JWTResponse;
import com.example.gorski.messages.response.Message;
import com.example.gorski.services.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/app/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTProvider jwtProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody Login loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.addJWTToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new JWTResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@Valid @RequestBody Registration registrationRequest) {
        if (userRepository.existsByUserName(registrationRequest.getUsername())) {
            return new ResponseEntity<>(new Message("This username is already exist!"), HttpStatus.BAD_REQUEST);
        }

        User user = new User(registrationRequest.getUsername(), encoder.encode(registrationRequest.getPassword()),
                registrationRequest.getUserGender());

        Set<Role> roles = new HashSet<>();
        if(registrationRequest.getRole() != null) {
            Set<String> strRoles = registrationRequest.getRole();

            strRoles.forEach(role -> {
                if ("admin".equals(role)) {
                    Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("User role not find!"));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("User role not find!"));
                    roles.add(userRole);
                }
            });
        } else {
            // when user register
            Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("User role not find!"));
            roles.add(userRole);
        }

        user.setRoles(roles);
        userRepository.save(user);

        return new ResponseEntity<>(new Message("User "+ registrationRequest.getUsername() + " is registered successfully!"), HttpStatus.OK);
    }

}
