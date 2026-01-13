package com.commerce.coupons.service.validation;

import com.commerce.coupons.dto.request.BuyXGetYRulesRequest;
import com.commerce.coupons.dto.request.CartWiseRuleRequest;
import com.commerce.coupons.dto.request.CreateCouponRequest;
import com.commerce.coupons.enums.CouponType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CouponCreateValidator {

  private CouponCreateValidator() {}

  public static void validate(CreateCouponRequest req) {
    forbidOtherRules(req);
    Object rules = req.getType().extractRule(req);

    switch (req.getType()) {
    case BUY_X_GET_Y -> BuyXGetYValidator.validate((List<BuyXGetYRulesRequest>) rules);
    case CART_WISE_PERCENTAGE, CART_WISE_FLAT_AMOUNT -> CartWiseValidator.validate(
        (CartWiseRuleRequest) rules,
        req.getType()
    );
    default -> throw new IllegalArgumentException("Unsupported coupon type");
    }
  }

  private static void forbidOtherRules(CreateCouponRequest req) {

    CouponType type = req.getType();
    CouponType.RuleFamily allowedFamily = type.getRuleFamily();

    Object allowedRule = type.extractRule(req);
    if (allowedRule == null) {
      throw new IllegalArgumentException(
          type + " coupon requires its corresponding rule " + allowedFamily.getRequestFieldName() + " to be provided."
      );
    }

    for (CouponType t : CouponType.values()) {
      if (t.getRuleFamily() != allowedFamily && t.extractRule(req) != null) {
        throw new IllegalArgumentException(
            "Only rule allowed for coupon type " + type +
                " is " + allowedFamily.getRequestFieldName() + " ."
        );
      }
    }
  }

}
