package com.commerce.coupons.model.rules;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
public class BuyXGetYRule {

  /*
  * Assuming we are applying this rule to a specific product only.
  * If needed, this can be extended to a list of products or categories.
  * productId should be a foreign key reference to the products table in a full implementation.
  */
  @Column(nullable = false)
  private UUID productId;

  @Column(nullable = false)
  private int buyQuantity;

  @Column(nullable = false)
  private int getQuantity;
}
