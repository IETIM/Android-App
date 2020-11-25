package com.ieti.ui.stores;

import android.os.Bundle;
import android.view.Menu;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ieti.persistence.ShopPersistence;
import com.ieti.persistence.impl.ShopPersistenceImpl;
import com.ieti.ui.R;
import com.ieti.ui.products.cart.CartOption;

public class StoreActivity extends AppCompatActivity {

    private ShopPersistence persistence = ShopPersistenceImpl.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_view);
        String category = getIntent().getStringExtra("category");
        getSupportActionBar().setTitle(category);
        ((GridView)findViewById(R.id.grid)).setAdapter(new StoreAdapter(this,persistence.getShopsByCategory(category)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        CartOption.onClickCart(menu, this);
        return true;
    }
}
