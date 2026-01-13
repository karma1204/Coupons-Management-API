package com.commerce.coupons.model.rules;

import com.commerce.coupons.model.Coupon;
import com.commerce.coupons.model.entity.ProductQuantity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bxgy_rules")
public class BuyXGetYRule {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "coupon_id", nullable = false)
  @JsonIgnore
  private Coupon coupon;

  @ElementCollection
  @CollectionTable(
      name = "bxgy_buy_products",
      joinColumns = @JoinColumn(name = "rule_set_id")
  )
  private Set<ProductQuantity> buyProducts = new HashSet<>();

  @ElementCollection
  @CollectionTable(
      name = "bxgy_get_products",
      joinColumns = @JoinColumn(name = "rule_set_id")
  )
  private Set<ProductQuantity> getProducts = new HashSet<>();

  private int repetitionLimit;

  public BuyXGetYRule(Set<ProductQuantity> buyProducts, Set<ProductQuantity> getProducts, int repetitionLimit) {
    this.buyProducts = buyProducts;
    this.getProducts = getProducts;
    this.repetitionLimit = repetitionLimit;
  }
}


