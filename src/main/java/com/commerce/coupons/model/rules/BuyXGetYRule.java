package com.commerce.coupons.model.rules;

import com.commerce.coupons.model.entity.ProductQuantity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BuyXGetYRule {

  @ElementCollection
  @CollectionTable(
      name = "bxgy_buy_products",
      joinColumns = @JoinColumn(name = "coupon_id")
  )
  private List<ProductQuantity> buyProducts;

  @ElementCollection
  @CollectionTable(
      name = "bxgy_get_products",
      joinColumns = @JoinColumn(name = "coupon_id")
  )
  private List<ProductQuantity> getProducts;

  @Column(nullable = false)
  private int repetitionLimit;
}

