package com.commerce.coupons.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;

@MappedSuperclass
public class BaseEntity {

  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false, updatable = false)
  private Instant updatedAt;

  @PrePersist
  protected void onCreate() {
    Instant now = Instant.now();
    this.createdAt = now;
    this.updatedAt = now;
    validate();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = Instant.now();
    validate();
  }

  protected void validate() {
    // Default implementation does nothing.
    // Subclasses can override this method to implement custom validation logic.
  }
}
