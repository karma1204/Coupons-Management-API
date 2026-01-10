package com.commerce.coupons.model.rules;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class BuyXGetYRule {

  /*
  * Assuming we are applying this rule to a specific product only.
  * If needed, this can be extended to a list of products or categories.
  */
  @Column(nullable = false)
  private UUID productId;

  @Column(nullable = false)
  private int buyQuantity;

  @Column(nullable = false)
  private int getQuantity;
}
