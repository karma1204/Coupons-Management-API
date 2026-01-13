package com.commerce.coupons.enums;

import com.commerce.coupons.dto.request.CreateCouponRequest;

import java.util.function.Function;

public enum CouponType {

    BUY_X_GET_Y(
        RuleFamily.BUY_X_GET_Y,
        CreateCouponRequest::getBuyXGetYRules
    ),

    CART_WISE_PERCENTAGE(
        RuleFamily.CART_WISE,
        CreateCouponRequest::getCartWiseRule
    ),

    CART_WISE_FLAT_AMOUNT(
        RuleFamily.CART_WISE,
        CreateCouponRequest::getCartWiseRule
    );

    public enum RuleFamily {
        BUY_X_GET_Y("buyXGetYRules"),
        CART_WISE("cartWiseRule");

        private final String requestFieldName;

        RuleFamily(String requestFieldName) {
            this.requestFieldName = requestFieldName;
        }

        public String getRequestFieldName() {
            return requestFieldName;
        }
    }

    private final RuleFamily ruleFamily;
    private final Function<CreateCouponRequest, Object> extractor;

    CouponType(RuleFamily ruleFamily,
        Function<CreateCouponRequest, Object> extractor) {
        this.ruleFamily = ruleFamily;
        this.extractor = extractor;
    }

    public RuleFamily getRuleFamily() {
        return ruleFamily;
    }

    public Object extractRule(CreateCouponRequest req) {
        return extractor.apply(req);
    }
}


