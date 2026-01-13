package com.commerce.coupons.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CartWiseRuleResponse {
  private Double thresholdAmount;
  private Double percentageDiscount;
  private Double maxDiscountAmount;
  private Double flatDiscountAmount;
  private int maxApplications;
}
