package com.ieti.persistence;

import com.ieti.model.Product;
import java.util.List;

public interface ProductPersistence {
    List<Product> getProducts(String idShop);
}