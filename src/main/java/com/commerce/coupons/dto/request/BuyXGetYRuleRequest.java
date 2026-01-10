package com.commerce.coupons.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class BuyXGetYRuleRequest {

  @NotNull(message = "productId is required")
  private UUID productId;

  @NotNull(message = "buyQuantity is required")
  @Min(value = 1, message = "buyQuantity must be at least 1")
  private Integer buyQuantity;

  @NotNull(message = "getQuantity is required")
  @Min(value = 1, message = "getQuantity must be at least 1")
  private Integer getQuantity;
}
