package com.commerce.coupons.service;

import com.commerce.coupons.dto.request.CreateCouponRequest;
import com.commerce.coupons.dto.response.CouponResponse;
import com.commerce.coupons.dto.response.CouponsResponse;
import com.commerce.coupons.enums.CouponType;

import java.util.UUID;

public interface CouponService {

  CouponResponse createCoupon(CreateCouponRequest request);

  CouponResponse getCouponById(UUID id);

  CouponsResponse getCoupons(int offset, int limit, Boolean active, String code, CouponType type);

  CouponResponse updateCoupon(UUID id, CreateCouponRequest request);
}
