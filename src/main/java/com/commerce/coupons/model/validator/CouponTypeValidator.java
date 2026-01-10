package com.commerce.coupons.model.validator;

import com.commerce.coupons.model.Coupon;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.stream.Stream;

@NoArgsConstructor
public final class CouponTypeValidator {

  public static void validate(Coupon coupon) {
    ensureExactlyOneRulePresent(coupon);
    ensureRuleMatchesType(coupon);
  }

  private static void ensureExactlyOneRulePresent(Coupon coupon) {
    long count = Stream.of(
            coupon.getBuyXGetYRule()
            // Add other rules here as they are implemented
        ).filter(Objects::nonNull)
        .count();

    if (count != 1) {
      throw new IllegalStateException(
          "Exactly one coupon rule must be specified"
      );
    }
  }

  private static void ensureRuleMatchesType(Coupon coupon) {
    Object expectedRule = switch (coupon.getType()) {
      case CART_WISE_PERCENTAGE_DISCOUNT -> null;
      case CART_WISE_FIXED_AMOUNT_DISCOUNT -> null;
      case PRODUCT_WISE_PERCENTAGE_DISCOUNT -> null;
      case PRODUCT_WISE_FIXED_AMOUNT_DISCOUNT -> null;
      case BUY_X_GET_Y -> coupon.getBuyXGetYRule();
    };

    if (expectedRule == null) {
      throw new IllegalStateException(
          "Coupon rule does not match coupon type: " + coupon.getType()
      );
    }
  }
}

