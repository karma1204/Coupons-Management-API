package com.commerce.coupons.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartWiseRuleRequest {

  @Min(value = 1, message = "Threshold amount must be non-negative.")
  private Double thresholdAmount;

  private Double percentageDiscount;

  private Double maxDiscountAmount;

  private Double flatDiscountAmount;

  @Min(value = 1, message = "Max applications must be at least 1")
  private int maxApplications;
}
