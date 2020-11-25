package com.ieti.persistence.impl;

import com.ieti.map.service.PlaceService;
import com.ieti.model.Shop;
import com.ieti.persistence.ShopPersistence;
import com.ieti.services.ShopService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ShopPersistenceHttpImpl implements ShopPersistence {

    private ShopService service;

    private static ShopPersistenceHttpImpl instance = null;

    public static ShopPersistenceHttpImpl getInstance(){
        if(instance==null){
            instance = new ShopPersistenceHttpImpl();
        }
        return instance;
    }

    private ShopPersistenceHttpImpl(){
        service = new Retrofit.Builder().
                baseUrl("https://ieti-deep-backend.herokuapp.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(ShopService.class);
    }


    @Override
    public List<Shop> getShops() {
        try {
            return service.getShops().execute().body();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<String> getCategories() {
        ArrayList<String> categories = new ArrayList<>();
        for(Shop shop:getShops()){
            if(!existCategory(categories,shop.getType())) categories.add(shop.getType());
        }
        return categories;
    }

    private boolean existCategory(List<String> categories,String category){
        for(String cat: categories){
            if(cat.equals(category)) return  true;
        }
        return false;
    }

    @Override
    public List<Shop> getShopsByCategory(String type) {
        try {
            return service.getShopsByType(type).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Shop getShopById(String id) {
        try {
            return service.getShopById(id).execute().body();
        }catch (Exception e){
            return null;
        }
    }
}
