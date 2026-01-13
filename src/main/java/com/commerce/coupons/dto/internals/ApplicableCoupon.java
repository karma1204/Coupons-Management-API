package com.commerce.coupons.dto.internals;

import com.commerce.coupons.model.Coupon;

public record ApplicableCoupon(
    Coupon coupon,
    double discountAmount
) {}

