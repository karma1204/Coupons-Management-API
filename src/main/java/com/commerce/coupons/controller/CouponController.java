package com.commerce.coupons.controller;

import com.commerce.coupons.dto.request.CreateCouponRequest;
import com.commerce.coupons.dto.response.CouponResponse;
import com.commerce.coupons.service.CouponService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

  private final CouponService couponService;

  public CouponController(CouponService couponService) {
    this.couponService = couponService;
  }

  @PostMapping
  public ResponseEntity<CouponResponse> createCoupon(
      @Valid @RequestBody CreateCouponRequest request
  ) {
    CouponResponse coupon = couponService.createCoupon(request);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(coupon);
  }
}
