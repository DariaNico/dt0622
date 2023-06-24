package com.dt0622.thetoolrental;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dt0622.thetoolrental.repository.*;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.dt0622.thetoolrental.model.*;

@SpringBootTest
class TheToolRentalApplicationTests {

	@Autowired
	private ToolRepository toolRepo;

	@Autowired
	private ToolTypeRepository toolTypeRepo;

	private ToolType ladder;
	private ToolType chainsaw;
	private ToolType jackhammer;

	@BeforeEach
	public void repoSetup() {
		toolTypeRepo.deleteAll();
		toolRepo.deleteAll();
		toolTypeRepo.save(new ToolType("Ladder", 1.99f, true, true, false));
		toolTypeRepo.save(new ToolType("Chainsaw", 1.49f, true, false, true));
		toolTypeRepo.save(new ToolType("Jackhammer", 2.99f, true, false, false));

		ladder = toolTypeRepo.findById("Ladder").get();
		chainsaw = toolTypeRepo.findById("Chainsaw").get();
		jackhammer = toolTypeRepo.findById("Jackhammer").get();

		toolRepo.save(new Tool("CHNS", chainsaw, "Stihl"));
		toolRepo.save(new Tool("LADW", ladder, "Werner"));
		toolRepo.save(new Tool("JAKD", jackhammer, "DeWalt"));
		toolRepo.save(new Tool("JAKR", jackhammer, "Ridgid"));
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void whenSavingANewTool_thenToolTypeShouldBeAccessible() {
		Tool newTool = new Tool("CHND", chainsaw, "DeWalt");
		toolRepo.save(newTool);

		ToolType savedToolType = toolRepo.findByToolCode(newTool.getToolCode()).get().getToolType();
		Assertions.assertEquals("Chainsaw", savedToolType.getId());
	}

	@Test
	public void whenRunningTest1__thenAnExceptionIsThrown() {
		Checkout checkout = new Checkout("JAKR", 5, 101, LocalDate.of(2015, 9, 3));

		assertEquals(1, 0);
	}

	@Test
	public void whenRunningTest2__thenAnExceptionIsThrown() {
		Checkout checkout = new Checkout("LADW", 3, 10, LocalDate.of(2020, 2, 7));

		assertEquals(1, 0);
	}

	@Test
	public void whenRunningTest3__thenAnExceptionIsThrown() {
		Checkout checkout = new Checkout("CHNS", 5, 25, LocalDate.of(2015, 2, 7));

		assertEquals(1, 0);
	}

	@Test
	public void whenRunningTest4__thenAnExceptionIsThrown() {
		Checkout checkout = new Checkout("JAKD", 6, 0, LocalDate.of(2015, 9, 3));

		assertEquals(1, 0);
	}

	@Test
	public void whenRunningTest5__thenAnExceptionIsThrown() {
		Checkout checkout = new Checkout("JAKR", 9, 0, LocalDate.of(2015, 2, 7));

		assertEquals(1, 0);
	}

	@Test
	public void whenRunningTest6__thenAnExceptionIsThrown() {
		Checkout checkout = new Checkout("JAKR", 4, 50, LocalDate.of(2020, 2, 7));

		assertEquals(1, 0);
	}
}
