package com.commerce.coupons.strategy;

import com.commerce.coupons.enums.CouponType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CouponStrategyRegistry {

  private final Map<CouponType, CouponApplicationStrategy> strategies;

  public CouponStrategyRegistry(
      List<CouponApplicationStrategy> strategyList
  ) {
    this.strategies = strategyList.stream()
        .collect(Collectors.toMap(
            CouponApplicationStrategy::supportedType,
            Function.identity()
        ));
  }

  public CouponApplicationStrategy getStrategy(CouponType type) {
    CouponApplicationStrategy strategy = strategies.get(type);
    if (strategy == null) {
      throw new IllegalStateException(
          "No strategy registered for coupon type " + type
      );
    }
    return strategy;
  }
}

