package com.commerce.coupons.strategy.application;

import com.commerce.coupons.dto.internals.ApplicableCoupon;
import com.commerce.coupons.enums.CouponType;
import com.commerce.coupons.model.Coupon;
import com.commerce.coupons.model.common.ProductQuantity;
import com.commerce.coupons.model.rules.BuyXGetYRule;
import com.commerce.coupons.strategy.CouponApplicationStrategy;
import com.commerce.coupons.strategy.helper.CartContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BuyXGetYStrategy implements CouponApplicationStrategy {

  @Override
  public CouponType supportedType() {
    return CouponType.BUY_X_GET_Y;
  }

  @Override
  public Optional<ApplicableCoupon> apply(
      Coupon coupon,
      CartContext ctx
  ) {

    double totalDiscount = 0;

    for (BuyXGetYRule rule : coupon.getBuyXGetYRules()) {

      int possibleApplications = Integer.MAX_VALUE;

      for (ProductQuantity buy : rule.getBuyProducts()) {
        int inCart = ctx.quantity(buy.getProductId());
        possibleApplications =
            Math.min(possibleApplications, inCart / buy.getQuantity());
      }

      if (possibleApplications <= 0) continue;

      possibleApplications =
          Math.min(possibleApplications, rule.getRepetitionLimit());

      for (ProductQuantity get : rule.getGetProducts()) {
        double price = ctx.price(get.getProductId());
        totalDiscount +=
            possibleApplications * get.getQuantity() * price;
      }
    }

    return totalDiscount > 0
        ? Optional.of(new ApplicableCoupon(coupon, totalDiscount))
        : Optional.empty();
  }
}
