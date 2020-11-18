package com.ieti.persistence.impl;

import com.ieti.model.Product;
import com.ieti.model.Purchase;
import com.ieti.persistence.CartPersistence;

import java.util.ArrayList;
import java.util.List;

public class CartPersistenceImpl implements CartPersistence {
    private List<Purchase> purchases;

    public CartPersistenceImpl() {
        purchases = new ArrayList<>();
    }

    @Override
    public List<Purchase> getPurchases() {
        return null;
    }

    @Override
    public void addPurchase(Purchase purchase) {
        purchases.add(purchase);
    }

    @Override
    public void deletePurchase(Purchase purchase) {
        purchases.remove(purchase);
    }

    @Override
    public void deletePurchases() {

    }
}
