package com.dt0622.thetoolrental.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.dt0622.thetoolrental.service.ToolRentalDaysCalculator;

import jakarta.persistence.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Entity
@Table(name = "rentalAgreements")
public class RentalAgreement {
  @Id
  @GeneratedValue
  private Long id;
  // Tool code - Specified at checkout
  @Column(name = "toolCode")
  private String toolCode;
  // Tool type - From tool info
  @Column(name = "toolTypeId")
  private String toolTypeId;
  // Tool brand - From tool info
  @Column(name = "toolBrand")
  private String toolBrand;
  // Rental days - Specified at checkout
  @Column(name = "rentalDays")
  private int rentalDays;
  // Check out date - Specified at checkout
  @Column(name = "checkOutDate")
  private LocalDate checkOutDate;
  // Due date - Calculated from checkout date and rental days.
  @Column(name = "dueDate")
  private LocalDate dueDate;
  // Daily rental charge - Amount per day, specified by the tool type.
  @Column(name = "dailyRentalCharge")
  private float dailyRentalCharge;
  // Charge days - Count of chargeable days, from day after checkout
  // through and including due date, excluding “no charge” days as specified
  // by the tool type.
  @Column(name = "chargeDays")
  private int chargeDays;
  // Pre-discount charge - Calculated as charge days X daily charge.
  // Resulting total rounded half up to cents
  @Column(name = "preDiscountCharge")
  private float preDiscountCharge;
  // Discount percent - Specified at checkout.
  @Column(name = "discountPercent")
  private int discountPercent;
  // Discount amount - calculated from discount % and pre-discount
  // charge.Resulting amount rounded half up to cents.
  @Column(name = "discountAmount")
  private float discountAmount;
  // Final charge - Calculated as pre-discount charge - discount amount.
  @Column(name = "finalCharge")
  private float finalCharge;

  private static final Logger log = LoggerFactory.getLogger(RentalAgreement.class);

  protected RentalAgreement() {
  }

  public RentalAgreement(Checkout checkout, Tool tool) {
    ToolType toolType = tool.getToolType();

    this.toolCode = tool.getToolCode();
    this.toolTypeId = toolType.getId();
    this.toolBrand = tool.getBrand();
    this.rentalDays = checkout.getRentalDays();
    this.checkOutDate = checkout.getCheckOutDate();

    this.dueDate = checkOutDate.plusDays(rentalDays);
    this.dailyRentalCharge = toolType.getDailyCharge();

    this.chargeDays = new ToolRentalDaysCalculator(toolType, checkOutDate, rentalDays).calculateChargeDays();
    this.preDiscountCharge = roundMoney((float) chargeDays * dailyRentalCharge);
    this.discountPercent = checkout.getDiscountPercent();
    this.discountAmount = roundMoney((float) discountPercent / 100 * preDiscountCharge);
    this.finalCharge = preDiscountCharge - discountAmount;
  }

  @Bean
  public CommandLineRunner logToConsole() {
    return (args) -> {
      DecimalFormat df = new DecimalFormat("###,###,###,###.##");
      log.info("Logging RentalAgreement Details:");
      log.info("-------------------------------");
      log.info(String.format("Tool code: %s", toolCode));
      log.info(String.format("Tool type: %s", toolTypeId));
      log.info(String.format("Tool brand: %s", toolBrand));
      log.info(String.format("Rental days: %d days", rentalDays));
      log.info(String.format("Check out date: %s", formatLocalDate(checkOutDate)));
      log.info(String.format("Due date: %s", formatLocalDate(dueDate)));
      log.info(String.format("Daily rental charge: $%f", df.format(dailyRentalCharge)));
      log.info(String.format("Charge days: %d days", chargeDays));
      log.info(String.format("Pre-discount charge: $%f", df.format(preDiscountCharge)));
      log.info(String.format("Discount percent: %d%%", discountPercent));
      log.info(String.format("Discount amount: $%f", df.format(discountAmount)));
      log.info(String.format("Final charge: $%f", df.format(finalCharge)));
      log.info("-------------------------------");
      log.info("");
    };
  }

  private float roundMoney(float floatToRound) {
    return BigDecimal.valueOf(floatToRound).setScale(2, RoundingMode.HALF_UP).floatValue();
  }

  private String formatLocalDate(LocalDate localDate) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
    return localDate.format(dateTimeFormatter);
  }
}

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