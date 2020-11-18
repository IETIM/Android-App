package com.ieti.persistence;

import com.ieti.model.Product;
import com.ieti.model.Purchase;
import java.util.List;

public interface CartPersistence {
    List<Purchase> getPurchases();
    void addPurchase(Purchase product);
    void deletePurchase(Purchase purchase);
    void deletePurchases();
}
