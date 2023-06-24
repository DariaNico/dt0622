package com.dt0622.thetoolrental.service;

import org.springframework.stereotype.Service;

// TODO: review this file
//import jakarta.validation.constraints.Max;
//import jakarta.validation.constraints.NotNull;

//import java.lang.Math;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Calendar;
//import java.time.temporal.ChronoField;
import java.time.DayOfWeek;
import java.time.LocalDate;

// TODO: rename to RentalCalculator
public class HolidayCalculator {
  private LocalDate startDate;
  private LocalDate endDate;
  private int startYear;
  private int endYear;

  public HolidayCalculator(LocalDate startDate, LocalDate endDate) {
    // LocalDate maxStartDate = endDate.minusDays(1);
    // @Max(maxStartDate);
    this.startDate = startDate;
    this.startYear = startDate.getYear();
    this.endDate = endDate;
    this.endYear = endDate.getYear();
  }

  public int totalHolidaysCountWithinRentalRange() {
    int independenceDayCount = numberOfHolidayCountFor(calculateObservedIndependenceDateFor(startYear),
        calculateObservedIndependenceDateFor(endYear));
    int laborDayCount = numberOfHolidayCountFor(calculateObservedLaborDateFor(startYear),
        calculateObservedLaborDateFor(endYear));

    return laborDayCount + independenceDayCount;
  }

  private int numberOfHolidayCountFor(LocalDate startYearHolidayDate, LocalDate endYearHolidayDate) {
    int independenceDayCount = 0;

    int yearDifference = endYear - startYear;
    boolean isStartYearHolidayWithinRentalRange = isDateWithinDateRange(startYearHolidayDate);
    boolean isEndYearHolidayWithinRentalRange = isDateWithinDateRange(endYearHolidayDate);

    if (yearDifference > 0) {
      if (isStartYearHolidayWithinRentalRange) {
        independenceDayCount++;
      }
      if (isEndYearHolidayWithinRentalRange) {
        independenceDayCount++;
      }
      independenceDayCount += yearDifference - 1;
    } else if (yearDifference == 0) {
      if (isStartYearHolidayWithinRentalRange || isEndYearHolidayWithinRentalRange) {
        independenceDayCount++;
      }
    } else {
      // TODO: throw error because this is wrong
      return 0;
    }

    return independenceDayCount;
  }

  private boolean isDateWithinDateRange(LocalDate comparingDate) {
    boolean isDateAfterOrOnStartDate = startDate.isBefore(comparingDate)
        || startDate.isEqual(comparingDate);
    boolean isDateBeforeOrOnEndDate = endDate.isAfter(comparingDate)
        || endDate.isEqual(comparingDate);
    return isDateAfterOrOnStartDate && isDateBeforeOrOnEndDate;
  }

  private static LocalDate calculateObservedIndependenceDateFor(int year) {
    LocalDate independenceDate = LocalDate.parse(String.format("%d-07-04", year));
    DayOfWeek independenceDay = independenceDate.getDayOfWeek();

    LocalDate observedHolidayDate;

    if (independenceDay == DayOfWeek.SUNDAY) {
      // Closest day is the next Monday
      observedHolidayDate = independenceDate.plusDays(1);
    } else if (independenceDay == DayOfWeek.SATURDAY) {
      // Closest day is the previous Friday
      observedHolidayDate = independenceDate.minusDays(1);
    } else {
      // not on a weekend and thus no observed day
      observedHolidayDate = independenceDate;
    }

    return observedHolidayDate;
  }

  private static LocalDate calculateObservedLaborDateFor(int year) {
    LocalDate observedHolidayDate;
    LocalDate startOfSeptemberDate = LocalDate.parse(String.format("%d-09-01", year));
    int startOfSeptemberDayValue = startOfSeptemberDate.getDayOfWeek().getValue();

    if (startOfSeptemberDayValue > 1) {
      int daysToAddInt = 8 - startOfSeptemberDayValue;
      Long daysToAdd = Long.valueOf(daysToAddInt);

      observedHolidayDate = startOfSeptemberDate.plusDays(daysToAdd);
    } else {
      // First of Sept is a Monday
      observedHolidayDate = startOfSeptemberDate;
    }

    return observedHolidayDate;
  }
}