package com.commerce.coupons.dto.response;

import com.commerce.coupons.enums.CouponType;
import com.commerce.coupons.model.Coupon;
import com.commerce.coupons.model.rules.BuyXGetYRule;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class CouponResponse {

  private UUID id;
  private CouponType type;
  private String name;
  private String code;
  private String details;
  private boolean active;
  private Instant validFrom;
  private Instant validTill;
  private BuyXGetYRuleResponse buyXGetYRule;

  public static CouponResponse from(Coupon coupon) {
    CouponResponse response = new CouponResponse();
    response.id = coupon.getId();
    response.type = coupon.getType();
    response.name = coupon.getName();
    response.code = coupon.getCode();
    response.details = coupon.getDetails();
    response.active = coupon.isActive();
    response.validFrom = coupon.getValidFrom();
    response.validTill = coupon.getValidTill();

    switch (coupon.getType()) {
    case BUY_X_GET_Y -> response.buyXGetYRule = mapBuyXGetYRule(coupon.getBuyXGetYRule());
    }

    return response;
  }

  private static BuyXGetYRuleResponse mapBuyXGetYRule(BuyXGetYRule rule) {
    return new BuyXGetYRuleResponse(
        rule.getProductId(),
        rule.getBuyQuantity(),
        rule.getGetQuantity()
    );
  }
}
