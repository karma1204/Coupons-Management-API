package com.commerce.coupons.service.validation;

import com.commerce.coupons.model.rules.BuyXGetYRule;

public class BuyXGetYValidator {

  public static void validate(BuyXGetYRule rule){
    if( rule.getBuyProducts() == null || rule.getBuyProducts().isEmpty()){
      throw new IllegalArgumentException("Buy products list cannot be null or empty.");
    }
    if( rule.getGetProducts() == null || rule.getGetProducts().isEmpty()){
      throw new IllegalArgumentException("Get products list cannot be null or empty.");
    }
    if( rule.getRepetitionLimit() < 1){
      throw new IllegalArgumentException("Repetition limit must be at least 1.");
    }
  }
}
