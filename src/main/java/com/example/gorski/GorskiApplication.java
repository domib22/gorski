package com.example.gorski;

import com.example.gorski.domain.users.User;
import com.example.gorski.domain.users.UserGender;
import com.example.gorski.domain.users.UserRepository;
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
			userRepository.save(new User("User", "$2y$12$Gsf7AIAc0xNnVCnzlVhePeSuFIgmC1I.jS0v7ERVQtkfBmjGYvZ7m", UserGender.WOMAN));
			userRepository.save(new User("User2", "$2y$12$cP0eQoLH.os1IxYFI/PIIupYhNJ3fpOyrYMIXZJ1rnuMyXbMX333W", UserGender.MAN));
		};

	}
}
