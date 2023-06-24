package com.dt0622.thetoolrental.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "toolTypes")
public class ToolType {
  // Id is the tool type name
  @Id
  private String id;
  @Min(value = 0, message = "Value should be a positive number")
  @Column(name = "dailyCharge", nullable = false)
  private float dailyCharge;
  @Column(name = "weekdayCharge", columnDefinition = "boolean default true", nullable = false)
  private Boolean weekdayCharge;
  @Column(name = "weekendCharge", columnDefinition = "boolean default true", nullable = false)
  private Boolean weekendCharge;
  @Column(name = "holidayCharge", columnDefinition = "boolean default false", nullable = false)
  private Boolean holidayCharge;

  protected ToolType() {
  }

  public ToolType(String id, float dailyCharge, Boolean weekdayCharge, Boolean weekendCharge, Boolean holidayCharge) {
    this.id = id;
    this.dailyCharge = dailyCharge;
    this.weekdayCharge = weekdayCharge;
    this.weekendCharge = weekendCharge;
    this.holidayCharge = holidayCharge;
  }

  // getters
  public String getId() {
    return id;
  }

  public float getDailyCharge() {
    return dailyCharge;
  }

  public Boolean getWeekdayCharge() {
    return weekdayCharge;
  }

  public Boolean getWeekendCharge() {
    return weekendCharge;
  }

  public Boolean getHolidayCharge() {
    return holidayCharge;
  }

  // setters
  public void setId(String id) {
    this.id = id;
  }

  public void setDailyCharge(float dailyCharge) {
    this.dailyCharge = dailyCharge;
  }

  public void setWeekdayCharge(Boolean weekdayCharge) {
    this.weekdayCharge = weekdayCharge;
  }

  public void setWeekendCharge(Boolean weekendCharge) {
    this.weekendCharge = weekendCharge;
  }

  public void setHolidayCharge(Boolean holidayCharge) {
    this.holidayCharge = holidayCharge;
  }

  @Override
  public String toString() {
    return String.format(
        "ToolType[id=%s, dailyCharge='%f', weekdayCharge='%b', weekendCharge='%b', holidayCharge='%b']",
        id, dailyCharge, weekdayCharge, weekendCharge, holidayCharge);
  }
}
