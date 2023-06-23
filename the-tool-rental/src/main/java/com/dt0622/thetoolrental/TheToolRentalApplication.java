package com.dt0622.thetoolrental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class TheToolRentalApplication {

	@GetMapping("/")
	public String index() {
		return "Welcome to the Tool Rental!";
	}

	public static void main(String[] args) {
		SpringApplication.run(TheToolRentalApplication.class, args);
	}

}
