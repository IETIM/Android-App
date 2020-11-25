package com.ieti.persistence.impl;

import com.ieti.model.Product;
import com.ieti.persistence.ProductPersistence;
import com.ieti.services.ProductService;
import com.ieti.services.ShopService;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ProductPersistenceHttpImpl implements ProductPersistence {

    private static ProductPersistenceHttpImpl instance = null;
    private ProductService productService;


    public static ProductPersistenceHttpImpl getInstance(){
        if (instance == null) {
            instance = new ProductPersistenceHttpImpl();
        }

        return instance;
    }
    private ProductPersistenceHttpImpl() {
        productService = new Retrofit.Builder().
                baseUrl("https://ieti-deep-backend.herokuapp.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(ProductService.class);
    }

    @Override
    public List<Product> getProducts(String idShop) {
        try {
            return productService.getProductsByShop(idShop).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Product getProductById(String id) {
        try {
            return productService.getProductById(id).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
