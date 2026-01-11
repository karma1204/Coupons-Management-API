package com.commerce.coupons.model;

import com.commerce.coupons.enums.CouponType;
import com.commerce.coupons.model.rules.BuyXGetYRule;
import com.commerce.coupons.model.validator.CouponTypeValidator;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
      name = "coupons",
      uniqueConstraints = {
          @UniqueConstraint(name = "uk_coupons_name", columnNames = "name"),
          @UniqueConstraint(name = "uk_coupons_code", columnNames = "code")
      }
    )
@Getter
@Setter
@NoArgsConstructor
public class Coupon extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private CouponType type;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(nullable = false, length = 20)
  private String code;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String details;

  @Column(nullable = false)
  private boolean active;

  @Column(nullable = false)
  private Instant validFrom;

  @Column
  private Instant validTill; // null means no expiration or a long time in the future

  @Embedded
  private BuyXGetYRule buyXGetYRule;

  @Override
  protected void validate() {
    super.validate();
    validateValidity();
    validateCouponConfiguration();
  }

  private void validateValidity() {
    if (validTill != null && validTill.isBefore(validFrom)) {
      throw new IllegalStateException(
          "validTill cannot be before validFrom"
      );
    }
  }

  private void validateCouponConfiguration() {
    CouponTypeValidator.validate(this);
  }

  @Builder
  public Coupon(CouponType type, String name, String code, String details,
                boolean active, Instant validFrom, Instant validTill,
                BuyXGetYRule buyXGetYRule) {
    this.type = type;
    this.name = name;
    this.code = code;
    this.details = details;
    this.active = active;
    this.validFrom = validFrom;
    this.validTill = validTill;
    this.buyXGetYRule = buyXGetYRule;
  }
}
