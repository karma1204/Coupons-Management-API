package com.commerce.coupons.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class BuyXGetYRuleResponse {

  private List<UUID> productIds;
  private int buyQuantity;
  private int getQuantity;
}
