package com.commerce.coupons.service.impl;

import com.commerce.coupons.dto.common.ProductQuantityDTO;
import com.commerce.coupons.dto.request.BuyXGetYRulesRequest;
import com.commerce.coupons.dto.request.CreateCouponRequest;
import com.commerce.coupons.enums.CouponType;
import com.commerce.coupons.model.Coupon;
import com.commerce.coupons.model.entity.ProductQuantity;
import com.commerce.coupons.model.rules.BuyXGetYRule;
import com.commerce.coupons.model.rules.CartWiseRule;
import com.commerce.coupons.service.validation.CouponCreateValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class CouponFactory {

  private CouponFactory() {}
  static Coupon createCouponEntity(CreateCouponRequest request) {
    CouponCreateValidator.validate(request);
    log.info("Coupon validated successfully for code={}", request.getCode());
    Coupon coupon = new Coupon(
        request.getType(),
        request.getName(),
        request.getCode().toUpperCase(),
        request.getDetails(),
        request.getActive(),
        request.getValidFrom(),
        request.getValidTill()
    );

    log.info("Mapping coupon rules for type={}", request.getType());
    mapCouponRules(request, coupon);
    log.info("Coupon rules mapped successfully for type={}", request.getType());

    return coupon;
  }

  static void updateCouponEntity(Coupon coupon, CreateCouponRequest request) {
    CouponCreateValidator.validate(request);
    log.info("Coupon validated successfully for code={}", request.getCode());
    coupon.setName(request.getName());
    coupon.setCode(request.getCode().toUpperCase());
    coupon.setDetails(request.getDetails());
    coupon.setActive(request.getActive());
    coupon.setValidFrom(request.getValidFrom());
    coupon.setValidTill(request.getValidTill());

    log.info("Mapping coupon rules for type={}", request.getType());
    mapCouponRules(request, coupon);
    log.info("Coupon rules mapped successfully for type={}", request.getType());
  }

  static void mapCouponRules(CreateCouponRequest request, Coupon coupon){
    switch (request.getType()) {
    case BUY_X_GET_Y -> coupon.addBuyXGetYRules(mapBuyXGetYRules(request.getBuyXGetYRules()));
    case CART_WISE_PERCENTAGE ->
        coupon.setCartWiseRule(
            new CartWiseRule(
                coupon,
                request.getCartWiseRule().getThresholdAmount(),
                request.getCartWiseRule().getPercentageDiscount(),
                request.getCartWiseRule().getMaxDiscountAmount(),
                request.getCartWiseRule().getMaxApplications()
            )
        );
    case CART_WISE_FLAT_AMOUNT ->
        coupon.setCartWiseRule(
            new CartWiseRule(
                coupon,
                request.getCartWiseRule().getThresholdAmount(),
                request.getCartWiseRule().getFlatDiscountAmount(),
                request.getCartWiseRule().getMaxApplications()
            )
        );
    }
  }

  static List<BuyXGetYRule> mapBuyXGetYRules(List<BuyXGetYRulesRequest> requests) {
    if (requests == null || requests.isEmpty()) {
      return List.of();
    }

    return requests.stream()
        .map(CouponFactory::mapBuyXGetYRule)
        .toList();
  }

  static BuyXGetYRule mapBuyXGetYRule(BuyXGetYRulesRequest req) {
    return new BuyXGetYRule(
        mapProducts(req.getBuyProducts()),
        mapProducts(req.getGetProducts()),
        req.getRepetitionLimit()
    );
  }

  static Set<ProductQuantity> mapProducts(
      Set<ProductQuantityDTO> products
  ) {
    return products.stream()
        .map(dto -> new ProductQuantity(dto.getProductId(), dto.getQuantity()))
        .collect(Collectors.toSet());
  }

  static Specification<Coupon> couponsCriteria(Boolean active, String code, CouponType type) {
    return (root, query, cb) -> {
      var predicates = cb.conjunction();

      if (code != null && !code.isBlank()) {
        predicates = cb.and(predicates, cb.equal(cb.upper(root.get("code")), code.toUpperCase()));
      }

      if (type != null) {
        predicates = cb.and(predicates, cb.equal(root.get("type"), type));
      }

      if (active != null) {
        predicates = cb.and(predicates, cb.equal(root.get("active"), active));
      }

      return predicates;
    };
  }
}
