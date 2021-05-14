package com.example.gorski.domain.users;

import com.example.gorski.domain.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String username);
    Boolean existsByUserName(String username);
}
