package com.dt0622.thetoolrental.service;

import org.springframework.stereotype.Service;
import java.lang.Math;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Calendar;
import java.time.DayOfWeek;
import java.time.temporal.ChronoField;

@Service
// TODO: rename to RentalCalculator
public class HolidayCalculator {
  private LocalDate startDate;
  private LocalDate endDate;
  private SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yy");
  private int startYear;
  private int startMonth;
  private int startDay;
  private int endYear;
  private int endMonth;
  private int endDay;
  private int rentalYearDifference;

  public HolidayCalculator(LocalDate startDate, LocalDate endDate) {
    this.startDate = startDate;
    this.startYear = startDate.getYear();
    this.startMonth = startDate.getMonthValue();
    this.startDay = startDate.getDayOfMonth();

    this.endDate = endDate;
    this.endYear = endDate.getYear();
    this.endMonth = endDate.getMonthValue();
    this.endDay = endDate.getDayOfMonth();

    this.rentalYearDifference = this.endYear - this.startYear;
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

  public int numberOfHolidays() {
    return 0;
  }
}