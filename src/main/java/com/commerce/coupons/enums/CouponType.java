package com.commerce.coupons.enums;

import com.commerce.coupons.dto.request.CreateCouponRequest;

import java.util.function.Function;

public enum CouponType {
    BUY_X_GET_Y(CreateCouponRequest::getBuyXGetYRule);

    private final Function<CreateCouponRequest, Object> ruleExtractor;

    CouponType(Function<CreateCouponRequest, Object> ruleExtractor) {
        this.ruleExtractor = ruleExtractor;
    }

    public Object extractRule(CreateCouponRequest req) {
        return ruleExtractor.apply(req);
    }
}
