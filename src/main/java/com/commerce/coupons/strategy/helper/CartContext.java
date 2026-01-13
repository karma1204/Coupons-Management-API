package com.commerce.coupons.strategy.helper;

import com.commerce.coupons.dto.request.CartItemRequest;
import com.commerce.coupons.dto.request.CartRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CartContext {

  private final Map<UUID, Integer> quantityByProduct;
  private final Map<UUID, Double> priceByProduct;
  private final double cartTotal;

  public CartContext(CartRequest cart) {
    quantityByProduct = new HashMap<>();
    priceByProduct = new HashMap<>();

    for (CartItemRequest item : cart.getItems()) {
      quantityByProduct.merge(
          item.getProductId(),
          item.getQuantity(),
          Integer::sum
      );
      priceByProduct.put(item.getProductId(), item.getPrice());
    }

    cartTotal = cart.getCartTotal();
  }

  public int quantity(UUID productId) {
    return quantityByProduct.getOrDefault(productId, 0);
  }

  public double price(UUID productId) {
    return priceByProduct.getOrDefault(productId, 0.0);
  }

  public double cartTotal() {
    return cartTotal;
  }
}

