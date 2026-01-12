package com.commerce.coupons.service;

import com.commerce.coupons.dto.request.CreateCouponRequest;
import com.commerce.coupons.dto.response.CouponResponse;

import java.util.UUID;

public interface CouponService {

  CouponResponse createCoupon(CreateCouponRequest request);

  CouponResponse getCouponById(UUID id);
}
