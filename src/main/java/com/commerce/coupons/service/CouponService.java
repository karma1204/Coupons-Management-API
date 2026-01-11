package com.commerce.coupons.service;

import com.commerce.coupons.dto.request.CreateCouponRequest;
import com.commerce.coupons.dto.response.CouponResponse;

public interface CouponService {

  CouponResponse createCoupon(CreateCouponRequest request);
}
