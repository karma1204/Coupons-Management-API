package com.commerce.coupons.model.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductQuantity {

  @Column(nullable = false, columnDefinition = "BINARY(16)")
  private UUID productId;

  @Column(nullable = false)
  private int quantity;
}

