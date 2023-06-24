package com.dt0622.thetoolrental.model;

import java.time.LocalDate;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.hibernate.validator.constraints.Range;

import com.dt0622.thetoolrental.exception.ResourceNotFoundException;
import com.dt0622.thetoolrental.repository.ToolRepository;

//import com.dt0622.thetoolrental.service.HolidayCalculator;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "checkouts")
public class Checkout {
  // Unique id associated with this Checkout transaction
  @Id
  @GeneratedValue
  private Long id;
  // Tool code - Same toolCode associated with tools
  @Column(name = "toolCode")
  private String toolCode;
  // Rental day count - The number of days for which the customer wants to rent
  // the tool. (e.g. 4 days)
  @Min(value = 1, message = "User must Checkout the tool for at least 1 whole rentalDays.")
  @Column(name = "rentalDays", columnDefinition = "default '1'")
  private int rentalDays;
  // Discount percent - As a whole number, 0-100 (e.g. 20 = 20%)
  @Range(min = 0, max = 100, message = "The tool Checkout discountPercent must be a whole number between 0 and 100.")
  @Column(name = "discountPercent")
  private int discountPercent;
  // Check out day - Initial day of tool checkout
  @Column(name = "checkOutDate")
  private LocalDate checkOutDate;

  protected Checkout() {
  }

  // invoke
  public Checkout(String toolCode, int rentalDays, int discountPercent, LocalDate checkOutDate) {
    this.toolCode = toolCode;
    this.rentalDays = rentalDays;
    this.discountPercent = discountPercent;
    this.checkOutDate = checkOutDate;
  }

  // getters
  public Long getId() {
    return id;
  }

  public String getToolCode() {
    return toolCode;
  }

  public int getRentalDays() {
    return rentalDays;

  }

  public int getDiscountPercent() {
    return discountPercent;

  }

  public LocalDate getCheckOutDate() {
    return checkOutDate;
  }

  // setters
  public void setToolCode(String toolCode) {
    this.toolCode = toolCode;
  }

  public void setRentalDays(int rentalDays) {
    this.rentalDays = rentalDays;

  }

  public void setDiscountPercent(int discountPercent) {
    this.discountPercent = discountPercent;

  }

  public void setCheckOutDate(LocalDate checkOutDate) {
    this.checkOutDate = checkOutDate;
  }

  public RentalAgreement drafRentalAgreement(ToolRepository toolRepository) {
    Optional<Tool> tool = toolRepository.findByToolCode(toolCode);

    try {
      validateToolExists(tool, toolCode);
      return new RentalAgreement(this, tool.get());
    } catch (ResourceNotFoundException ex) {
      System.out.println("ResourceNotFoundException occured: " + ex);
      return new RentalAgreement(this, null);
    }
  }

  static void validateToolExists(Optional<Tool> tool, String toolCode) throws ResourceNotFoundException {
    if (!tool.isPresent()) {
      throw new ResourceNotFoundException(String.format("Tool with tool code '%s' does not exist", toolCode));
    }
  }
}