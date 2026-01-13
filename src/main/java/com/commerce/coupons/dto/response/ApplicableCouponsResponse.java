package com.commerce.coupons.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ApplicableCouponsResponse {
  private List<ApplicableCouponItemDto> applicableCoupons;
}
