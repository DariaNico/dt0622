package com.dt0622.thetoolrental.helper;

// Note: DayOfWeek.getDayOfWeek().getValue() returns an int where Monday (1) and Sunday (7)
import java.time.DayOfWeek;
import java.time.LocalDate;

// FIXME: rename this to HolidayAndWeekendCalculator or something more fitting
public class HolidayCalculator {
  private LocalDate startDate;
  private LocalDate endDate;
  private int dayRangeSize;
  private int startYear;
  private int endYear;

  public HolidayCalculator(LocalDate startDate, LocalDate endDate, int dayRangeSize) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.dayRangeSize = dayRangeSize;
    this.startYear = startDate.getYear();
    this.endYear = endDate.getYear();
  }

  public int totalHolidaysCountWithinDateRange() {
    int independenceDayCount = numberOfHolidayCountFor(calculateObservedIndependenceDateFor(startYear),
        calculateObservedIndependenceDateFor(endYear));
    int laborDayCount = numberOfHolidayCountFor(calculateObservedLaborDateFor(startYear),
        calculateObservedLaborDateFor(endYear));

    return laborDayCount + independenceDayCount;
  }

  public int totalWeekendDaysCountWithinDateRange() {
    int weekendDaysCount = 0;
    int startDateDayOfWeekValue = startDate.getDayOfWeek().getValue();
    int endDateDayOfWeekValue = endDate.getDayOfWeek().getValue();

    int startDaysToNextWeekend = exclusiveNumDaysToNextWeekend(startDateDayOfWeekValue);
    int endDaysToLastWeekend = inclusiveNumDaysToLastWeekend(endDateDayOfWeekValue);
    int totalDaysToWeekend = startDaysToNextWeekend + endDaysToLastWeekend;

    if (dayRangeSize < 7) {
      if (dayRangeSize > startDaysToNextWeekend) {
        weekendDaysCount += dayRangeSize - totalDaysToWeekend;
      }
    } else {
      int startDaysToEndOfWeek = 7 - startDateDayOfWeekValue;
      int fullWeeks = (dayRangeSize - startDaysToEndOfWeek - endDateDayOfWeekValue) / 7;
      int startWeekWeekendCount = startDaysToEndOfWeek > 2 ? 2 : startDaysToEndOfWeek;
      int endWeekWeekendCount = endDateDayOfWeekValue < 6 ? 0 : endDateDayOfWeekValue - 5;

      weekendDaysCount += startWeekWeekendCount + endWeekWeekendCount + (2 * fullWeeks);
    }

    return weekendDaysCount;
  }

  private int exclusiveNumDaysToNextWeekend(int dayOfWeekValue) {
    if (dayOfWeekValue < 6) {
      return 5 - dayOfWeekValue;
    } else {
      return 0;
    }
  }

  private int inclusiveNumDaysToLastWeekend(int dayOfWeekValue) {
    if (dayOfWeekValue < 6) {
      return dayOfWeekValue;
    } else {
      return 0;
    }
  }

  private int numberOfHolidayCountFor(LocalDate startYearHolidayDate, LocalDate endYearHolidayDate) {
    int independenceDayCount = 0;

    int yearDifference = endYear - startYear;
    boolean isStartYearHolidayWithinRange = isDateWithinDateRange(startYearHolidayDate);
    boolean isEndYearHolidayWithinRange = isDateWithinDateRange(endYearHolidayDate);

    if (yearDifference > 0) {
      if (isStartYearHolidayWithinRange) {
        independenceDayCount++;
      }
      if (isEndYearHolidayWithinRange) {
        independenceDayCount++;
      }
      independenceDayCount += yearDifference - 1;
    } else if (yearDifference == 0) {
      if (isStartYearHolidayWithinRange || isEndYearHolidayWithinRange) {
        independenceDayCount++;
      }
    } else {
      // Invalid scenario where endDate was before startDate
      // FIXME: add validator or throw error to ensure this is handled in a better way
      return -1;
    }

    return independenceDayCount;
  }

  private boolean isDateWithinDateRange(LocalDate comparingDate) {
    boolean isDateAfterStartDate = startDate.isBefore(comparingDate);
    boolean isDateBeforeOrOnEndDate = endDate.isAfter(comparingDate)
        || endDate.isEqual(comparingDate);
    return isDateAfterStartDate && isDateBeforeOrOnEndDate;
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