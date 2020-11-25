package com.ieti.services;

import com.ieti.model.Product;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductService {
    @GET("products/{idShop}")
    Call<List<Product>> getProductsByShop(@Path("idShop") String idShop);

    @GET("products/id/{idProduct}")
    Call<Product> getProductById(@Path("idProduct") String idProduct);
}
