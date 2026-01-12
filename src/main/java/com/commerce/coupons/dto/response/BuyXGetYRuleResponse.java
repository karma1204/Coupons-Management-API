package com.commerce.coupons.dto.response;

import com.commerce.coupons.model.entity.ProductQuantity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class BuyXGetYRuleResponse {

  private List<ProductQuantity> buyProducts;
  private List<ProductQuantity> getProducts;
  private int repetitionLimit;
}
