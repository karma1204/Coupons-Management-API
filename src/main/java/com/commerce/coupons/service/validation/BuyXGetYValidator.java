package com.commerce.coupons.service.validation;

import com.commerce.coupons.model.rules.BuyXGetYRule;

public class BuyXGetYValidator {

  public static void validate(BuyXGetYRule rule){
    if (rule.getBuyQuantity() <= 0) {
      throw new IllegalArgumentException("Buy quantity must be greater than zero for Buy X Get Y rule");
    }
    if (rule.getGetQuantity() <= 0) {
      throw new IllegalArgumentException("Get quantity must be greater than zero for Buy X Get Y rule");
    }
  }
}
