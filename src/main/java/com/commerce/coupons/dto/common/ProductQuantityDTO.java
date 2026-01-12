package com.commerce.coupons.dto.common;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductQuantityDTO {

  @NotNull
  private UUID productId;

  @Min(1)
  private int quantity;
}
