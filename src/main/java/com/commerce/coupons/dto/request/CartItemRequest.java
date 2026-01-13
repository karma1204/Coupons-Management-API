package com.commerce.coupons.dto.request;

import com.commerce.coupons.dto.common.ProductQuantityDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequest extends ProductQuantityDTO {
  private Double price;
}
