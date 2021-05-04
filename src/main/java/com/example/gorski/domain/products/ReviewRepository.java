package com.example.gorski.domain.products;

import com.example.gorski.domain.products.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserName(String userName);
}
