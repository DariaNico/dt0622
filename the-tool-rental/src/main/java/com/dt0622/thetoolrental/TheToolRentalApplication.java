package com.dt0622.thetoolrental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.dt0622.thetoolrental.repository.*;
import com.dt0622.thetoolrental.model.*;

@RestController
@SpringBootApplication
public class TheToolRentalApplication {

	@GetMapping("/")
	public String index() {
		return "Welcome to the Tool Rental!";
	}

	private static final Logger log = LoggerFactory.getLogger(TheToolRentalApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TheToolRentalApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(ToolRepository toolRepo, ToolTypeRepository toolTypeRepo) {
		return (args) -> {
			// Save tool types
			toolTypeRepo.save(new ToolType("Ladder", 1.99f, true, true, false));
			toolTypeRepo.save(new ToolType("Chainsaw", 1.49f, true, false, true));
			toolTypeRepo.save(new ToolType("Jackhammer", 2.99f, true, false, false));

			ToolType ladder = toolTypeRepo.findById("Ladder").get();
			ToolType chainsaw = toolTypeRepo.findById("Chainsaw").get();
			ToolType jackhammer = toolTypeRepo.findById("Jackhammer").get();

			// log info about all tool types
			log.info("ToolTypes found with findAll():");
			log.info("-------------------------------");
			for (ToolType toolType : toolTypeRepo.findAll()) {
				log.info(toolType.toString());
			}
			log.info("");

			log.info("ToolTypes found with findByDailyCharge():");
			log.info("-------------------------------");
			for (ToolType toolType : toolTypeRepo.findByDailyCharge(1.99f)) {
				log.info(toolType.toString());
			}
			log.info("");

			// Save tools
			toolRepo.save(new Tool("CHNS", chainsaw, "Stihl"));
			toolRepo.save(new Tool("LADW", ladder, "Werner"));
			toolRepo.save(new Tool("JAKD", jackhammer, "DeWalt"));
			toolRepo.save(new Tool("JAKR", jackhammer, "Ridgid"));

			// log info about all tools
			log.info("Tools found with findAll():");
			log.info("-------------------------------");
			for (Tool tool : toolRepo.findAll()) {
				log.info(tool.toString());
				log.info(tool.getToolType().toString());
			}
			log.info("");
		};
	}
}
