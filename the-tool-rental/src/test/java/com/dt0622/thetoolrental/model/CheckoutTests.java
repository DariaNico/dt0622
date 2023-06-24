package com.dt0622.thetoolrental.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

//import javax.validation.*;

import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Assertions;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//import com.dt0622.thetoolrental.repository.*;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@SpringBootTest
public class CheckoutTests {
  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
  private Checkout targetCheckout = new Checkout(null, 1, 0, null);

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
}
