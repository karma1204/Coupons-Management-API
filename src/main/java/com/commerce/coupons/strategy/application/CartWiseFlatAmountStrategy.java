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
public class CartWiseFlatAmountStrategy implements CouponApplicationStrategy {

  @Override
  public CouponType supportedType() {
    return CouponType.CART_WISE_FLAT_AMOUNT;
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

    return Optional.of(new ApplicableCoupon(coupon, rule.getFlatDiscountAmount()));
  }
}

