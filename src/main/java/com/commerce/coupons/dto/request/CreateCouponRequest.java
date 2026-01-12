package com.commerce.coupons.dto.request;

import com.commerce.coupons.enums.CouponType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class CreateCouponRequest {

  @NotNull(message = "Coupon type is required")
  private CouponType type;

  @NotBlank(message = "Coupon name is required")
  @Size(max = 100, message = "Coupon name must not exceed 100 characters")
  private String name;

  @NotBlank(message = "Coupon code is required")
  @Size(max = 20, message = "Coupon code must not exceed 20 characters")
  private String code;

  @NotBlank(message = "Coupon details are required")
  private String details;

  /**
   * Whether the coupon should be active immediately after creation.
   * Or this can be an administrative flag to enable/disable the coupon
   * irrespective of its validity period.
   * This acts as an administrative enable/disable flag.
   */
  @NotNull(message = "Active flag must be specified")
  private Boolean active;

  /**
   * Using Instant so a coupon can have validity starting at a specific time of day
   * Like a flash sale starting at midnight or noon.
   */
  @NotNull(message = "validFrom is required")
  private Instant validFrom;

  /**
   * Optional end of validity.
   * If null, the coupon is considered valid indefinitely.
   */
  private Instant validTill;

  @Valid
  private BuyXGetYRuleRequest buyXGetYRule;

  @AssertTrue(message = "validFrom must be in the future")
  public boolean isValidFromInFuture() {
    return validFrom != null && validFrom.isAfter(Instant.now().minusSeconds(5));
  }

  @AssertTrue(message = "validTill cannot be before validFrom")
  public boolean isValidTillAfterValidFrom() {
    return validTill == null || !validTill.isBefore(validFrom);
  }
}
