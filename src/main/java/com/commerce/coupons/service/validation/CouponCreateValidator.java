package com.commerce.coupons.service.validation;

import com.commerce.coupons.dto.request.CreateCouponRequest;
import com.commerce.coupons.enums.CouponType;
import com.commerce.coupons.model.rules.BuyXGetYRule;
import org.springframework.stereotype.Component;

@Component
public class CouponCreateValidator {

  public void validate(CreateCouponRequest req) {
    validateByType(req);
  }

  private void validateByType(CreateCouponRequest req) {
    forbidOtherRules(req);
    Object rule = req.getType().extractRule(req);

    switch (req.getType()) {
    case BUY_X_GET_Y -> BuyXGetYValidator.validate((BuyXGetYRule) rule);
    default -> throw new IllegalArgumentException("Unsupported coupon type");
    }
  }

  private void forbidOtherRules(CreateCouponRequest req) {

    CouponType type = req.getType();
    Object allowedRule = type.extractRule(req);

    if (allowedRule == null) {
      throw new IllegalArgumentException(
          type + " Coupon requires its corresponding rule."
      );
    }

    for (CouponType t : CouponType.values()) {
      if (t != type && t.extractRule(req) != null) {
        throw new IllegalArgumentException(
            "Only rule allowed for coupon type " + type + " is " + type.name()
        );
      }
    }
  }


}
