package com.ieti.persistence;

import com.ieti.model.Shop;

import java.util.List;

public interface ShopPersistence {

    List<Shop> getShops();

    List<String> getCategories();

    List<Shop> getShopsByCategory(String type);

    Shop getShopById(String id);

}
