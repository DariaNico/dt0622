package com.dt0622.thetoolrental.model;

import jakarta.persistence.*;

// TODO: rename this to rentalData
@Entity
@Table(name = "toolTypes")
public class ToolType {
  // TODO: rename this to toolType
  // Id is the tool type name
  @Id
  private String id;

  @Column(name = "dailyCharge", precision = 2, nullable = false)
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

  @Override
  public String toString() {
    return String.format(
        "ToolType[id=%s, dailyCharge='%f', weekdayCharge='%b', weekendCharge='%b', holidayCharge='%b']",
        id, dailyCharge, weekdayCharge, weekendCharge, holidayCharge);
  }

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
}
