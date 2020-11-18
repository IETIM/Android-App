package com.ieti.map.service;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlaceService {
    @GET("v1/geocode?apiKey=0aizyNbKZY4J9HTyfyGWqiIltD1mmBqxngImkcvaBS8")
    Call<Places> getPlaces(@Query("q") String name);
}
