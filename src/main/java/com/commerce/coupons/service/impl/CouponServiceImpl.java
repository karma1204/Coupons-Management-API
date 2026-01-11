package com.commerce.coupons.service.impl;

import com.commerce.coupons.dto.request.CreateCouponRequest;
import com.commerce.coupons.dto.request.BuyXGetYRuleRequest;
import com.commerce.coupons.dto.response.CouponResponse;
import com.commerce.coupons.model.Coupon;
import com.commerce.coupons.model.rules.BuyXGetYRule;
import com.commerce.coupons.repository.CouponRepository;
import com.commerce.coupons.service.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class CouponServiceImpl implements CouponService {

  private final CouponRepository couponRepository;

  public CouponServiceImpl(CouponRepository couponRepository) {
    this.couponRepository = couponRepository;
  }

  @Override
  public CouponResponse createCoupon(CreateCouponRequest request) {
    log.info("Creating coupon with code={}", request.getCode());
    Coupon.CouponBuilder builder = Coupon.builder()
        .type(request.getType())
        .name(request.getName())
        .code(request.getCode())
        .details(request.getDetails())
        .active(request.getActive())
        .validFrom(request.getValidFrom())
        .validTill(request.getValidTill());

    // Attach rule based on type
    switch (request.getType()) {
      case BUY_X_GET_Y -> builder.buyXGetYRule(mapBuyXGetYRule(request.getBuyXGetYRule()));
    }

    Coupon coupon = builder.build();
    couponRepository.save(coupon);
    log.info("Created coupon - {} : {}", coupon.getId(), coupon.getName());
    return CouponResponse.from(coupon);
  }

  private BuyXGetYRule mapBuyXGetYRule(BuyXGetYRuleRequest ruleRequest) {
    return new BuyXGetYRule(
        ruleRequest.getProductId(),
        ruleRequest.getBuyQuantity(),
        ruleRequest.getGetQuantity()
    );
  }
}
