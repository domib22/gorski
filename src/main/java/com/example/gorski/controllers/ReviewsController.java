package com.example.gorski.controllers;

import com.example.gorski.domain.products.ReviewRepository;
import com.example.gorski.domain.products.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/app")
public class ReviewsController {
    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/reviews")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    @ResponseBody
    public Iterable<Review> getUserReviews(String username) {
        return reviewRepository.findByUserName(username);
    }

}
