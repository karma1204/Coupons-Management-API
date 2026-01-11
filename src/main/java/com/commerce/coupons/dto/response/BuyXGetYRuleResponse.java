package com.commerce.coupons.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class BuyXGetYRuleResponse {

  private UUID productId;
  private int buyQuantity;
  private int getQuantity;
}
