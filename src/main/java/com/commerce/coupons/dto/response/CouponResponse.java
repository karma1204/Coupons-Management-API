package com.commerce.coupons.dto.response;

import com.commerce.coupons.enums.CouponType;
import com.commerce.coupons.model.Coupon;
import com.commerce.coupons.model.rules.BuyXGetYRule;
import com.commerce.coupons.model.rules.CartWiseRule;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CouponResponse {

  private UUID id;
  private CouponType type;
  private String name;
  private String code;
  private String details;
  private boolean active;
  private Instant validFrom;
  private Instant validTill;
  private List<BuyXGetYRuleResponse> buyXGetYRules;
  private CartWiseRule cartWiseRule;

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
    case BUY_X_GET_Y -> response.buyXGetYRules = mapBuyXGetYRules(coupon.getBuyXGetYRules());
    case CART_WISE_PERCENTAGE, CART_WISE_FLAT_AMOUNT -> response.cartWiseRule = coupon.getCartWiseRule();
    }

    return response;
  }

  private static List<BuyXGetYRuleResponse> mapBuyXGetYRules(List<BuyXGetYRule> rules) {
    return rules.stream()
        .map(rule -> {
          return new BuyXGetYRuleResponse(
              rule.getBuyProducts(),
              rule.getGetProducts(),
              rule.getRepetitionLimit()
          );
        })
        .toList();
  }
}
