package com.dt0622.thetoolrental.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dt0622.thetoolrental.repository.ToolRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@SpringBootTest
public class CheckoutTests {
  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
  private Checkout targetCheckout = new Checkout(null, 1, 0, null);

  @Autowired
  private ToolRepository toolRepo;

  @Test
  public void whenSettingInvalidRentalDays_thenCheckoutThrowsAnErrorWithHelpfulMessage() throws Exception {
    targetCheckout.setRentalDays(0);
    Set<ConstraintViolation<Checkout>> constraintViolations = validator
        .validate(targetCheckout);
    assertEquals(1, constraintViolations.size());
    assertEquals("User must Checkout the tool for at least 1 whole rentalDays.",
        constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void whenSettingInvalidDiscountPercent_thenCheckoutThrowsAnErrorWithHelpfulMessage() throws Exception {
    targetCheckout.setDiscountPercent(-10);
    Set<ConstraintViolation<Checkout>> constraintViolations = validator
        .validate(targetCheckout);
    assertEquals(1, constraintViolations.size());
    assertEquals("The tool Checkout discountPercent must be a whole number between 0 and 100.",
        constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void whenDraftRentalAgreementRunsWithNonexistantTool__thenReturnsANull() {
    targetCheckout = new Checkout("404", 3, 10, LocalDate.of(2020, 2, 7));
    RentalAgreement draftedRentalAgreement = targetCheckout.draftRentalAgreement(toolRepo);

    assertNull(draftedRentalAgreement);
  }
}
