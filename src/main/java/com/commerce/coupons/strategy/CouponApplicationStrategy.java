package com.commerce.coupons.strategy;

import com.commerce.coupons.enums.CouponType;
import com.commerce.coupons.model.Coupon;
import com.commerce.coupons.dto.internals.ApplicableCoupon;
import com.commerce.coupons.strategy.helper.CartContext;

import java.util.Optional;

public interface CouponApplicationStrategy {

  CouponType supportedType();

  Optional<ApplicableCoupon> apply(
      Coupon coupon,
      CartContext ctx
  );
}
