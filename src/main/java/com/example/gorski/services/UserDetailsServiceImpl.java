package com.example.gorski.services;

import com.example.gorski.domain.users.model.User;
import com.example.gorski.domain.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) {
        User user = userRepository.findByUserName(userName);
                //.orElseThrow(() -> new UsernameNotFoundException("User: " + userName + " not found"));
        return UserPrinciple.build(user);
    }

}
