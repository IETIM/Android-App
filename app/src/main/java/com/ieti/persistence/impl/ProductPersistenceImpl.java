package com.ieti.persistence.impl;

import com.ieti.model.Product;
import com.ieti.model.Shop;
import com.ieti.persistence.ProductPersistence;
import com.ieti.persistence.ShopPersistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductPersistenceImpl implements ProductPersistence {

    public List<Product> products;

    private static ProductPersistenceImpl instance = null;

    private HashMap<String, List<Product>> productsByShop;

    public static ProductPersistenceImpl getInstance(){
        if (instance == null) {
            instance = new ProductPersistenceImpl();
        }

        return instance;
    }

    private ProductPersistenceImpl(){
        products = new ArrayList<>();
        products.add(new Product("Limón","5 Limones fresquitos.", 1000L));
        products.add(new Product("Papas Margarita","Paquete de papas sabor a pollo. (Cont. Neto 45g)", 1500L));
        products.add(new Product("Leche Alquería","Leche alquería deslactosa (Cont. Neto 1100 ml)", 3500L));
        products.add(new Product("Yogurt Alpina","Yogurt semidescremado con dulce, con melocotón y cultivos probióticos (Cont. Neto 200g)", 1800L));
        products.add(new Product("DeTodito","Detodito picante (Cont. Neto 45g)", 2000L));
    }


    @Override
    public List<Product> getProducts(String idShop) {
        return products;
    }
}
