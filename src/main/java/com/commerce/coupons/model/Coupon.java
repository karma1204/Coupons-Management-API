package com.commerce.coupons.model;

import com.commerce.coupons.enums.CouponType;
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
@AllArgsConstructor
@Builder
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
  private Instant validTill; // nullable means no expiration or a long time in the future
}
