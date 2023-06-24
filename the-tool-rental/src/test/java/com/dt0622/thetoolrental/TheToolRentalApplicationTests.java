package com.dt0622.thetoolrental;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dt0622.thetoolrental.repository.ToolRepository;
import com.dt0622.thetoolrental.repository.ToolTypeRepository;
import com.dt0622.thetoolrental.model.Tool;
import com.dt0622.thetoolrental.model.ToolType;

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
}
