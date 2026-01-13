package com.commerce.coupons.model.rules;

import com.commerce.coupons.model.Coupon;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CartWiseRule {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "coupon_id", nullable = false, unique = true)
  @JsonIgnore
  private Coupon coupon;

  @Column(nullable = false)
  private Double thresholdAmount;

  private Double percentageDiscount;

  private Double maxDiscountAmount;

  private Double flatDiscountAmount;

  @Column(nullable = false)
  private int maxApplications;

  public CartWiseRule(Coupon coupon, Double thresholdAmount, Double percentageDiscount, Double maxDiscountAmount,
                      int maxApplications) {
    this.coupon = coupon;
    this.thresholdAmount = thresholdAmount;
    this.percentageDiscount = percentageDiscount;
    this.maxDiscountAmount = maxDiscountAmount;
    this.maxApplications = maxApplications;
  }

  public CartWiseRule(Coupon coupon, Double thresholdAmount, Double flatDiscountAmount,
      int maxApplications) {
    this.coupon = coupon;
    this.thresholdAmount = thresholdAmount;
    this.flatDiscountAmount = flatDiscountAmount;
    this.maxApplications = maxApplications;
  }
}
