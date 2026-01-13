package com.commerce.coupons.dto.response;

import com.commerce.coupons.model.entity.ProductQuantity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class BuyXGetYRulesResponse {

  private Set<ProductQuantity> buyProducts;
  private Set<ProductQuantity> getProducts;
  private int repetitionLimit;
}
