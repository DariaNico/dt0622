package com.dt0622.thetoolrental;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dt0622.thetoolrental.repository.*;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import com.dt0622.thetoolrental.exception.ResourceNotFoundException;
import com.dt0622.thetoolrental.model.*;

@SpringBootTest
class TheToolRentalApplicationTests {

	@Autowired
	private ToolRepository toolRepo;

	@Autowired
	private ToolTypeRepository toolTypeRepo;

	@Autowired
	private CheckoutRepository checkoutRepo;

	@Autowired
	private RentalAgreementRepository rentalAgreementRepo;

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

	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	public Checkout targetCheckout = new Checkout(null, 1, 0, null);

	@Test
	public void whenRunningTest1__thenAnExceptionIsThrownDueToInvalidDiscountPercent() throws Exception {
		targetCheckout = new Checkout("JAKR", 5, 101, LocalDate.of(2015, 9, 3));

		Set<ConstraintViolation<Checkout>> constraintViolations = validator
				.validate(targetCheckout);
		assertEquals(1, constraintViolations.size());
		assertEquals("The tool Checkout discountPercent must be a whole number between 0 and 100.",
				constraintViolations.iterator().next().getMessage());
	}

	@Test
	public void whenRunningTest2AndTryingToDraftRentalAgreement__thenReturnsANullDueToNonexistantToolInRepository() {
		targetCheckout = new Checkout("LADW", 3, 10, LocalDate.of(2020, 7, 2));
		RentalAgreement draftedRentalAgreement = targetCheckout.draftRentalAgreement(toolRepo);

		draftedRentalAgreement.printToConsole();

		assertEquals(targetCheckout, draftedRentalAgreement.getCheckout());

		assertEquals("Tool code: LADW", draftedRentalAgreement.getFormattedToolCode());
		assertEquals("Tool type: Ladder", draftedRentalAgreement.getFormattedToolTypeId());
		assertEquals("Tool brand: Werner", draftedRentalAgreement.getFormattedToolBrand());
		assertEquals("Rental days: 3 days", draftedRentalAgreement.getFormattedRentalDays());
		assertEquals("Check out date: 07/02/20", draftedRentalAgreement.getFormattedCheckOutDate());
		assertEquals("Daily rental charge: $1.99", draftedRentalAgreement.getFormattedDailyRentalCharge());
		assertEquals("Charge days: 2 days", draftedRentalAgreement.getFormattedChargeDays());
		assertEquals("Pre-discount charge: $3.98", draftedRentalAgreement.getFormattedPreDiscountCharge());
		assertEquals("Discount percent: 10%", draftedRentalAgreement.getFormattedDiscountPercent());
		assertEquals("Discount amount: $0.40", draftedRentalAgreement.getFormattedDiscountAmount());
		assertEquals("Final charge: $3.58", draftedRentalAgreement.getFormattedFinalCharge());
	}

	// @Test
	// public void whenRunningTest3__thenAnExceptionIsThrown() {
	// Checkout checkout = new Checkout("CHNS", 5, 25, LocalDate.of(2015, 2, 7));

	// assertEquals(1, 0);
	// }

	// @Test
	// public void whenRunningTest4__thenAnExceptionIsThrown() {
	// Checkout checkout = new Checkout("JAKD", 6, 0, LocalDate.of(2015, 9, 3));

	// assertEquals(1, 0);
	// }

	// @Test
	// public void whenRunningTest5__thenAnExceptionIsThrown() {
	// Checkout checkout = new Checkout("JAKR", 9, 0, LocalDate.of(2015, 2, 7));

	// assertEquals(1, 0);
	// }

	// @Test
	// public void whenRunningTest6__thenAnExceptionIsThrown() {
	// Checkout checkout = new Checkout("JAKR", 4, 50, LocalDate.of(2020, 2, 7));

	// assertEquals(1, 0);
	// }
}
