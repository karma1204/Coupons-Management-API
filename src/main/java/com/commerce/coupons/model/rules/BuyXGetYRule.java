package com.commerce.coupons.model.rules;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuyXGetYRule {

  /*
  * Assuming we are applying this rule to a specific product only.
  * If needed, this can be extended to a list of products or categories.
  * productId should be a foreign key reference to the products table in a full implementation.
  */
  @ElementCollection
  @CollectionTable(
      name = "buy_x_get_y_products",
      joinColumns = @JoinColumn(name = "coupon_id")
  )
  @Column(name = "product_id", nullable = false, columnDefinition = "BINARY(16)")
  private List<UUID> productIds;


  @Column(nullable = false)
  private int buyQuantity;

  @Column(nullable = false)
  private int getQuantity;
}
