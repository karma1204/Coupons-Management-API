package com.commerce.coupons.service.impl;

import com.commerce.coupons.dto.request.CreateCouponRequest;
import com.commerce.coupons.dto.response.CouponResponse;
import com.commerce.coupons.dto.response.CouponsResponse;
import com.commerce.coupons.enums.CouponType;
import com.commerce.coupons.model.Coupon;
import com.commerce.coupons.repository.CouponRepository;
import com.commerce.coupons.service.CouponService;
import com.commerce.coupons.service.factory.CouponFactory;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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
    Coupon coupon = CouponFactory.createCouponEntity(request);
    coupon = couponRepository.save(coupon);
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
    Specification<Coupon> spec = CouponFactory.couponsCriteria(active, code, type);

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
    CouponFactory.updateCouponEntity(coupon, request);

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
}
