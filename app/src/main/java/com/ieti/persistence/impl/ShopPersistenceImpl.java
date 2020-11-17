package com.ieti.persistence.impl;

import com.ieti.model.Shop;
import com.ieti.persistence.ShopPersistence;
import java.util.ArrayList;
import java.util.List;

public class ShopPersistenceImpl  implements ShopPersistence {

    public ArrayList<Shop> shops;

    private static ShopPersistence instance = null;

    public static ShopPersistence getInstance(){
        if(instance==null){
            instance=new ShopPersistenceImpl();
        }
        return instance;
    }

    private ShopPersistenceImpl(){
        shops = new ArrayList<>();
        shops.add(new Shop("SuperStore","Colombia Cajica calle 8","Supermercado"));
        shops.add(new Shop("MeFarmacia","Colombia Cajica Vereda Canelon","Farmacia"));
        shops.add(new Shop("MeFarmacia2","Colombia Centro Comercial SantaFe","Farmacia"));
        shops.add(new Shop("MeFarmacia3","Colombia Chia Avenida Pradilla","Farmacia"));
        shops.add(new Shop("Farma2","Colombia Chia Hacienda Fontanar","Farmacia"));
    }


    @Override
    public List<Shop> getShops() {
        return shops;
    }

    @Override
    public List<String> getCategories() {
        ArrayList<String> categories = new ArrayList<>();
        for(Shop shop:shops){
            if(!existCategory(categories,shop.getType())) categories.add(shop.getType());
        }
        return categories;
    }

    @Override
    public List<Shop> getShopsByCategory(String type) {
        ArrayList<Shop> shopsFilters = new ArrayList<>();
        for(Shop shop:shops){
            if(shop.getType().equals(type)) shopsFilters.add(shop);
        }
        return shopsFilters;
    }

    @Override
    public Shop getShopById(String id) {
        for (Shop shop: shops) {
            if (shop.getId().equals(id)) return shop;
        }
        return null;
    }

    private boolean existCategory(List<String> categories,String category){
        for(String cat: categories){
            if(cat.equals(category)) return  true;
        }
        return false;
    }
}
