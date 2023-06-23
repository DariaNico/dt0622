package com.dt0622.thetoolrental.model;

import jakarta.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tools")
public class Tool {
  // Tool Code - Unique identifier for a tool instance
  @Id
  @Column(name = "toolCode")
  private String toolCode;

  /*
   * Tool Type - The type of tool. The type also specifies the daily rental
   * charge,
   * and the days for which the daily rental charge applies.
   */
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "toolType_id", referencedColumnName = "id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIgnore
  private ToolType toolType;

  // Brand - The brand of the ladder, chain saw or jackhammer.
  @Column(name = "brand")
  private String brand;

  protected Tool() {
  }

  public Tool(String toolCode, ToolType toolType, String brand) {
    this.toolCode = toolCode;
    this.toolType = toolType;
    this.brand = brand;
  }

  @Override
  public String toString() {
    return String.format(
        "Tool[toolCode=%s, toolType_id='%s', brand='%s']",
        toolCode, toolType.getId(), brand);
  }

  public String getToolCode() {
    return toolCode;
  }

  public ToolType getToolType() {
    return toolType;
  }

  public String getBrand() {
    return brand;
  }
}