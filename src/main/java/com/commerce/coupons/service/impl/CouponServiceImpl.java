package com.commerce.coupons.service.impl;

import com.commerce.coupons.dto.common.ProductQuantityDTO;
import com.commerce.coupons.dto.request.CreateCouponRequest;
import com.commerce.coupons.dto.request.BuyXGetYRuleRequest;
import com.commerce.coupons.dto.response.CouponResponse;
import com.commerce.coupons.dto.response.CouponsResponse;
import com.commerce.coupons.enums.CouponType;
import com.commerce.coupons.model.Coupon;
import com.commerce.coupons.model.entity.ProductQuantity;
import com.commerce.coupons.model.rules.BuyXGetYRule;
import com.commerce.coupons.repository.CouponRepository;
import com.commerce.coupons.service.CouponService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
        .code(request.getCode().toUpperCase())
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

  @Override
  public CouponResponse getCouponById(UUID id){
    Coupon coupon = couponRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("Coupon not found with id {}", id);
          return new EntityNotFoundException("Coupon not found with id " + id);
        });
    log.info("Fetched coupon by id {}", id);
    return CouponResponse.from(coupon);
  }

  @Override
  public CouponsResponse getCoupons(int offset, int limit, Boolean active, String code, CouponType type) {
    PageRequest pageRequest = PageRequest.of(
        offset / limit,
        limit,
        Sort.by("createdAt").descending()
    );
    Specification<Coupon> spec = couponsCriteria(active, code, type);

    Page<Coupon> page = couponRepository.findAll(spec, pageRequest);
    List<CouponResponse> coupons = page.getContent()
        .stream()
        .map(CouponResponse::from)
        .toList();
    log.info("Fetched {} coupons with filters - active: {}, code: {}, type: {}", coupons.size(), active, code, type);
    return new CouponsResponse(
        coupons,
        (int) page.getTotalElements(),
        page.hasNext()
    );
  }

  @Override
  public CouponResponse updateCoupon(UUID id, CreateCouponRequest request) {
    Coupon coupon = couponRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("Coupon not found with id {}", id);
          return new EntityNotFoundException("Coupon not found with id " + id);
        });

    log.info("Updating coupon with id={}", id);
    coupon.setType(request.getType());
    coupon.setName(request.getName());
    coupon.setCode(request.getCode().toUpperCase());
    coupon.setDetails(request.getDetails());
    coupon.setActive(request.getActive());
    coupon.setValidFrom(request.getValidFrom());
    coupon.setValidTill(request.getValidTill());

    // Update rule based on type
    switch (request.getType()) {
      case BUY_X_GET_Y -> coupon.setBuyXGetYRule(mapBuyXGetYRule(request.getBuyXGetYRule()));
    }

    couponRepository.save(coupon);
    log.info("Updated coupon - {} : {}", coupon.getId(), coupon.getName());
    return CouponResponse.from(coupon);
  }

  @Override
  public void deleteCoupon(UUID id) {
    if (!couponRepository.existsById(id)) {
      log.warn("Coupon not found with id {}", id);
      throw new EntityNotFoundException("Coupon not found with id " + id);
    }
    couponRepository.deleteById(id);
    log.info("Deleted coupon with id {}", id);
  }

  private BuyXGetYRule mapBuyXGetYRule(BuyXGetYRuleRequest req) {
    return new BuyXGetYRule(
        mapProducts(req.getBuyProducts()),
        mapProducts(req.getGetProducts()),
        req.getRepetitionLimit()
    );
  }

  private Set<ProductQuantity> mapProducts(
      Set<ProductQuantityDTO> dtos
  ) {
    return dtos.stream()
        .map(dto -> new ProductQuantity(dto.getProductId(), dto.getQuantity()))
        .collect(Collectors.toSet());
  }

  private Specification<Coupon> couponsCriteria(Boolean active, String code, CouponType type) {
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
