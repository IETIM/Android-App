package com.ieti.services;

import  com.ieti.model.Shop;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ShopService {
    @GET("shops")
    Call<List<Shop>> getShops();
    @GET("shops/{id}")
    Call<Shop> getShopById(@Path("id") String id);
    @GET("shops")
    Call<List<Shop>> getShopsByType(@Query("type") String type);
}
