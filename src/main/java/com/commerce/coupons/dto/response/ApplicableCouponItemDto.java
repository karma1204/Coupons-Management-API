package com.commerce.coupons.dto.response;

import com.commerce.coupons.enums.CouponType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ApplicableCouponItemDto {

  private UUID couponId;
  private CouponType type;
  private Double discountAmount;
}
