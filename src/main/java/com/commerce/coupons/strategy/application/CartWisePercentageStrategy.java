package com.commerce.coupons.strategy.application;

import com.commerce.coupons.dto.internals.ApplicableCoupon;
import com.commerce.coupons.enums.CouponType;
import com.commerce.coupons.model.Coupon;
import com.commerce.coupons.model.rules.CartWiseRule;
import com.commerce.coupons.strategy.CouponApplicationStrategy;
import com.commerce.coupons.strategy.helper.CartContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CartWisePercentageStrategy
    implements CouponApplicationStrategy {

  @Override
  public CouponType supportedType() {
    return CouponType.CART_WISE_PERCENTAGE;
  }

  @Override
  public Optional<ApplicableCoupon> apply(
      Coupon coupon,
      CartContext ctx
  ) {

    CartWiseRule rule = coupon.getCartWiseRule();

    if (ctx.cartTotal() < rule.getThresholdAmount() || rule.getMaxApplications() <= 0) {
      return Optional.empty();
    }

    double discount =
        ctx.cartTotal() * rule.getPercentageDiscount() / 100.0;

    if (rule.getMaxDiscountAmount() != null) {
      discount = Math.min(discount, rule.getMaxDiscountAmount());
    }

    return Optional.of(new ApplicableCoupon(coupon, discount));
  }
}

