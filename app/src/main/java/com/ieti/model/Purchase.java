package com.ieti.model;

import androidx.annotation.Nullable;

public class Purchase {
    private String id;
    private String productId;
    private int quantity;

    public Purchase() {
    }

    public Purchase(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Purchase)) return false;
        return getId().equals(((Purchase) obj).getId());
    }

    @Override
    public int hashCode() {
        return 20;
    }
}