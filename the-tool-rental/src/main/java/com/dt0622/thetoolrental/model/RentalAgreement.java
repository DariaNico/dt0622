package com.dt0622.thetoolrental.model;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "rentalAgreements")
public class RentalAgreement {
  @Id
  @GeneratedValue
  private Long id;
  // Tool code - Specified at checkout
  private String toolCode;
  // Tool type - From tool info
  private String toolTypeId;
  // Tool brand - From tool info
  private String toolBrand;
  // Rental days - Specified at checkout
  private int rentalDays;
  // Check out date - Specified at checkout
  private Date checkOutDate;
  // Due date - Calculated from checkout date and rental days.
  private Date dueDate;
  // Daily rental charge - Amount per day, specified by the tool type.
  private float dailyRentalCharge;
  // Charge days - Count of chargeable days, from day after checkout
  // through and including due date, excluding “no charge” days as specified
  // by the tool type.
  private int chargeDays;
  // Pre-discount charge - Calculated as charge days X daily charge.
  // Resulting total rounded half up to cents
  private float preDiscountCharge;
  // Discount percent - Specified at checkout.
  private int discountPercent;
  // Discount amount - calculated from discount % and pre-discount
  // charge.Resulting amount rounded half up to cents.
  private float discountAmount;
  // Final charge - Calculated as pre-discount chage - discount amount.
  private float finalCharge;
}
/// *
// * Tool Type - The type of tool. The type also specifies the daily rental
// * charge,
// * and the days for which the daily rental charge applies.
// */
// @ManyToOne(fetch = FetchType.LAZY, optional = false)
// @JoinColumn(name = "toolType_id", referencedColumnName = "id", nullable =
/// false)
// @OnDelete(action = OnDeleteAction.CASCADE)
// @JsonIgnore
// private ToolType toolType;

//// Brand - The brand of the ladder, chain saw or jackhammer.
// @Column(name = "brand")
// private String brand;

// protected Tool() {
// }

// public Tool(String toolCode, ToolType toolType, String brand) {
// this.toolCode = toolCode;
// this.toolType = toolType;
// this.brand = brand;
// }

// @Override
// public String toString() {
// return String.format(
// "Tool[toolCode=%s, toolType_id='%s', brand='%s']",
// toolCode, toolType.getId(), brand);
// }

// public String getToolCode() {
// return toolCode;
// }

// public ToolType getToolType() {
// return toolType;
// }

// public String getBrand() {
// return brand;
// }

// Rental Agreement should include a method that can print the above values as
// text to the console like
// this:
// Tool code: LADW
// Tool type: Ladder
// ...
// Final charge: $9.99
// with formatting as follows:
// ● Date mm/dd/yy
// ● Currency $9,999.99
// ● Percent 99%