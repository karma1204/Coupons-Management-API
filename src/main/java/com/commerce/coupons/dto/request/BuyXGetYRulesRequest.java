package com.commerce.coupons.dto.request;

import com.commerce.coupons.dto.common.ProductQuantityDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
public class BuyXGetYRulesRequest {

  @NotEmpty(message = "Purchase products list cannot be empty")
  private Set<@Valid ProductQuantityDTO> buyProducts;

  @NotEmpty(message = "Get products list cannot be empty")
  private Set<@Valid ProductQuantityDTO> getProducts;

  @Min(value = 1, message = "Repetition limit must be at least 1")
  private int repetitionLimit;
}
