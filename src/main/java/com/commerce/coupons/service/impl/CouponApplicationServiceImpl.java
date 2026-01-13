package com.commerce.coupons.service.impl;

import com.commerce.coupons.dto.internals.ApplicableCoupon;
import com.commerce.coupons.dto.request.CartRequest;
import com.commerce.coupons.dto.response.ApplicableCouponItemDto;
import com.commerce.coupons.dto.response.ApplicableCouponsResponse;
import com.commerce.coupons.model.Coupon;
import com.commerce.coupons.repository.CouponRepository;
import com.commerce.coupons.service.CouponApplicationService;
import com.commerce.coupons.strategy.CouponStrategyRegistry;
import com.commerce.coupons.strategy.helper.CartContext;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CouponApplicationServiceImpl implements CouponApplicationService {

  private final CouponRepository couponRepository;
  private final CouponStrategyRegistry registry;

  public CouponApplicationServiceImpl(CouponRepository couponRepository, CouponStrategyRegistry registry) {
    this.couponRepository = couponRepository;
    this.registry = registry;
  }

  public ApplicableCouponsResponse getApplicableCouponsForCart(
      CartRequest cart
  ) {
    List<Coupon> candidates = fetchCandidateCoupons();
    if (candidates.isEmpty()) {
      return new ApplicableCouponsResponse(List.of());
    }

    CartContext ctx = new CartContext(cart);

    List<ApplicableCouponItemDto> result =
        candidates
            .parallelStream()
            .map(coupon ->
                registry
                    .getStrategy(coupon.getType())
                    .apply(coupon, ctx)
            )
            .flatMap(Optional::stream)
            .sorted(Comparator
                .comparingDouble(ApplicableCoupon::discountAmount)
                .reversed()
            )
            .map(ac -> new ApplicableCouponItemDto(
                ac.coupon().getId(),
                ac.coupon().getType(),
                ac.discountAmount()
            ))
            .toList();

    return new ApplicableCouponsResponse(result);
  }

  public List<Coupon> fetchCandidateCoupons() {
    Instant now = Instant.now();

    Specification<Coupon> spec = (root, query, cb) ->
        cb.and(
            cb.lessThanOrEqualTo(root.get("validFrom"), now),
            cb.or(
                cb.isNull(root.get("validTill")),
                cb.greaterThanOrEqualTo(root.get("validTill"), now)
            ),
            cb.isTrue(root.get("active"))
        );

    return couponRepository.findAll(spec);
  }
}
