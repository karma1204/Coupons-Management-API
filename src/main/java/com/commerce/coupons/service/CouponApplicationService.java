package com.commerce.coupons.service;

import com.commerce.coupons.dto.request.CartRequest;
import com.commerce.coupons.dto.response.ApplicableCouponsResponse;

public interface CouponApplicationService {
  ApplicableCouponsResponse getApplicableCouponsForCart(CartRequest cart);
}
