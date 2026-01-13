package com.commerce.coupons.controller;

import com.commerce.coupons.dto.request.CartRequest;
import com.commerce.coupons.dto.response.ApplicableCouponsResponse;
import com.commerce.coupons.service.CouponApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CouponApplicationController {

  private final CouponApplicationService couponApplicationService;

  public CouponApplicationController(CouponApplicationService couponApplicationService) {
    this.couponApplicationService = couponApplicationService;
  }

  @GetMapping("/applicable-coupons")
  public ResponseEntity<ApplicableCouponsResponse> getApplicableCouponsForCart(
      @RequestBody @Valid CartRequest cart
  ) {
    ApplicableCouponsResponse response = couponApplicationService.getApplicableCouponsForCart(cart);
    return ResponseEntity.ok(response);
  }
}
