package com.commerce.coupons.service.validation;

import com.commerce.coupons.dto.request.BuyXGetYRulesRequest;

import java.util.List;

public class BuyXGetYValidator {

  private BuyXGetYValidator(){}

  public static void validate(List<BuyXGetYRulesRequest> rules){
    if(rules == null || rules.isEmpty()){
      throw new IllegalArgumentException("Buy X Get Y rules list cannot be null or empty.");
    }
    for(BuyXGetYRulesRequest rule : rules){
      validateRule(rule);
    }
  }

  private static void validateRule(BuyXGetYRulesRequest rule){
    if(rule.getBuyProducts() == null || rule.getBuyProducts().isEmpty()){
      throw new IllegalArgumentException("Buy products list in Buy X Get Y rule cannot be null or empty.");
    }
    if(rule.getGetProducts() == null || rule.getGetProducts().isEmpty()){
      throw new IllegalArgumentException("Get products list in Buy X Get Y rule cannot be null or empty.");
    }
    if(rule.getRepetitionLimit() < 1){
      throw new IllegalArgumentException("Repetition limit in Buy X Get Y rule must be at least 1.");
    }
  }
}
