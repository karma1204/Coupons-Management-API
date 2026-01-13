package com.commerce.coupons.service.validation;

import com.commerce.coupons.dto.request.CartWiseRuleRequest;
import com.commerce.coupons.enums.CouponType;

public class CartWiseValidator {

  CartWiseValidator() {}

  public static void validate(CartWiseRuleRequest rule, CouponType type) {
    if (rule == null) {
      throw new IllegalArgumentException("Cart wise rule cannot be null.");
    }
    validateThresholdAmount(rule.getThresholdAmount());

    if (type == CouponType.CART_WISE_PERCENTAGE) {
      validatePercentageDiscount(rule);
    } else if (type == CouponType.CART_WISE_FLAT_AMOUNT) {
      validateFlatDiscount(rule);
    } else {
      throw new IllegalArgumentException("Invalid coupon type for cart wise rule.");
    }
  }

  public static void validatePercentageDiscount(CartWiseRuleRequest rule) {
    Double percentageDiscount = rule.getPercentageDiscount();
    Double maxDiscountAmount = rule.getMaxDiscountAmount();
    Double flatDiscountAmount = rule.getFlatDiscountAmount();
    if(flatDiscountAmount != null){
      throw new IllegalArgumentException("Flat discount amount should not be provided for CART_WISE_PERCENTAGE type.");
    }
    if (percentageDiscount == null || percentageDiscount <= 0 || percentageDiscount > 100) {
      throw new IllegalArgumentException("Percentage discount must be greater than 0 and less than or equal to 100.");
    }
    if (maxDiscountAmount == null || maxDiscountAmount <= 0) {
      throw new IllegalArgumentException("Max discount amount must be greater than 0 when percentage discount is provided.");
    }
  }

  public static void validateThresholdAmount(Double thresholdAmount) {
    if (thresholdAmount == null || thresholdAmount <= 0) {
      throw new IllegalArgumentException("Threshold amount must be greater than 0.");
    }
  }

  public static void validateFlatDiscount(CartWiseRuleRequest rule) {
    Double flatDiscountAmount = rule.getFlatDiscountAmount();
    Double thresholdAmount = rule.getThresholdAmount();
    Double percentageDiscount = rule.getPercentageDiscount();
    Double maxDiscountAmount = rule.getMaxDiscountAmount();
    if(percentageDiscount != null || maxDiscountAmount != null){
      throw new IllegalArgumentException("Percentage discount and max discount amount should not be provided for CART_WISE_FLAT_AMOUNT type.");
    }
    if (flatDiscountAmount == null || flatDiscountAmount <= 0) {
      throw new IllegalArgumentException("Flat discount amount must be greater than 0.");
    }
    if (thresholdAmount <= flatDiscountAmount) {
      throw new IllegalArgumentException("Threshold amount must be greater than the flat discount amount.");
    }
  }
}
