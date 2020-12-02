package com.example.gorski;

import com.example.gorski.domain.User;
import com.example.gorski.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GorskiApplication {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(GorskiApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
//            Save demo data after start
			userRepository.save(new User("User", "user", "male"));
		};
	}
}
