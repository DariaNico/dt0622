package com.dt0622.thetoolrental.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.*;

import javax.swing.ToolTipManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dt0622.thetoolrental.model.ToolType;
import com.dt0622.thetoolrental.repository.ToolRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class ToolRentalDaysCalculatorTests {
  private ToolType weekdayOnlyToolType = new ToolType("Hammerspace", 0f, true, false, false);
  private ToolRentalDaysCalculator targetCalculator = new ToolRentalDaysCalculator(weekdayOnlyToolType,
      LocalDate.of(2023, 12, 31),
      1);

  @Test
  public void whenCalculatingChargeDaysOverA9DayRentalPeriodOfWeekdayOnlyCharges__thenItReturnsTheCorrectChargeDays() {
    targetCalculator.setStartDate(LocalDate.of(2015, 7, 2));
    targetCalculator.setRentalDays(9);
    targetCalculator.setToolTypeValues(weekdayOnlyToolType);

    int chargeDays = targetCalculator.calculateChargeDays();
    assertEquals(5, chargeDays);
  }
}
