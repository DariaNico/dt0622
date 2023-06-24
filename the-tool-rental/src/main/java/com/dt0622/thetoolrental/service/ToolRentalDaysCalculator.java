package com.dt0622.thetoolrental.service;

import java.time.LocalDate;

import com.dt0622.thetoolrental.model.ToolType;

// TODO: rename to ToolChargeDaysCalculator
public class ToolRentalDaysCalculator {
  private LocalDate startDate;
  private int rentalDays;

  private LocalDate endDate;
  private boolean shouldChargeWeekdays;
  private boolean shouldChargeWeekends;
  private boolean shouldChargeHolidays;

  protected ToolRentalDaysCalculator() {
  }

  public ToolRentalDaysCalculator(ToolType toolType, LocalDate startDate, int rentalDays) {
    this.startDate = startDate;
    this.rentalDays = rentalDays;

    this.endDate = startDate.plusDays(rentalDays);
    this.shouldChargeWeekdays = toolType.getWeekdayCharge();
    this.shouldChargeWeekends = toolType.getWeekendCharge();
    this.shouldChargeHolidays = toolType.getHolidayCharge();
  }

  // setters
  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
    this.endDate = startDate.plusDays(rentalDays);
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
    this.startDate = endDate.minusDays(rentalDays);
  }

  public void setRentalDays(int rentalDays) {
    this.rentalDays = rentalDays;
    this.endDate = startDate.plusDays(rentalDays);
  }

  public void setToolTypeValues(ToolType toolType) {
    this.shouldChargeWeekdays = toolType.getWeekdayCharge();
    this.shouldChargeWeekends = toolType.getWeekendCharge();
    this.shouldChargeHolidays = toolType.getHolidayCharge();
  }

  // main functionality
  public int calculateChargeDays() {
    int chargeDays = rentalDays;

    HolidayCalculator holidayCalculator = new HolidayCalculator(startDate, endDate, rentalDays);
    int weekendDaysCount = holidayCalculator.totalWeekendDaysCountWithinDateRange();
    // Note: Observed Holidays only ever fall on Weekdays
    int holidayDaysCount = holidayCalculator.totalHolidaysCountWithinDateRange();

    if (!shouldChargeWeekdays) {
      chargeDays = weekendDaysCount;
    } else {
      if (!shouldChargeHolidays) {
        chargeDays -= holidayDaysCount;
      }
    }
    if (!shouldChargeWeekends) {
      chargeDays -= weekendDaysCount;
    }

    return chargeDays;
  }

}
