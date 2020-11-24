package com.ieti.ui.stores;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ieti.model.Shop;
import com.ieti.persistence.ShopPersistence;
import com.ieti.persistence.impl.ShopPersistenceHttpImpl;
import com.ieti.persistence.impl.ShopPersistenceImpl;
import com.ieti.ui.R;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class StoreActivity extends AppCompatActivity {

    private ShopPersistence persistence = ShopPersistenceHttpImpl.getInstance();
    private Executor executor = Executors.newFixedThreadPool(1);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_view);
        String category = getIntent().getStringExtra("category");
        getSupportActionBar().setTitle(category);
        executor.execute(()->{
            List<Shop> shops = persistence.getShopsByCategory(category);
            runOnUiThread(()->{
                ((GridView)findViewById(R.id.grid)).setAdapter(new StoreAdapter(this,shops));
            });
        });
    }
}
