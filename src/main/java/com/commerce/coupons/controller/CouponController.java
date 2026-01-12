package com.commerce.coupons.controller;

import com.commerce.coupons.dto.request.CreateCouponRequest;
import com.commerce.coupons.dto.response.CouponsResponse;
import com.commerce.coupons.dto.response.CouponResponse;
import com.commerce.coupons.enums.CouponType;
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


  @GetMapping("/{id}")
  public ResponseEntity<CouponResponse> getCouponById(
      @PathVariable UUID id
  ) {
    CouponResponse coupon = couponService.getCouponById(id);
    return ResponseEntity.ok(coupon);
  }

  @GetMapping
  public ResponseEntity<CouponsResponse> getCoupons(
      @RequestParam(defaultValue = "0") int offset,
      @RequestParam(defaultValue = "20") int limit,
      @RequestParam(required = false) Boolean active,
      @RequestParam(required = false) String code,
      @RequestParam(required = false) CouponType type
  ) {
    CouponsResponse coupons = couponService.getCoupons(
        offset, limit, active, code, type
    );
    return ResponseEntity.ok(coupons);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CouponResponse> updateCoupon(
      @PathVariable UUID id,
      @Valid @RequestBody CreateCouponRequest request
  ) {
    CouponResponse updatedCoupon = couponService.updateCoupon(id, request);
    return ResponseEntity.ok(updatedCoupon);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCoupon(
      @PathVariable UUID id
  ) {
    couponService.deleteCoupon(id);
    return ResponseEntity.noContent().build();
  }
}
