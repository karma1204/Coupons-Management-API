package com.commerce.coupons.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CartRequest {
  private List<CartItemRequest> items;

  public double getCartTotal() {
    return items.stream()
        .mapToDouble(i -> i.getPrice() * i.getQuantity())
        .sum();
  }

}
