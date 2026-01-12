package com.commerce.coupons.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CouponsResponse {
  private final List<CouponResponse> coupons;
  private final int totalCount;
  private final boolean hasMoreResults;
}
