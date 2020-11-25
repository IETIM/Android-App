package com.ieti.ui.products;
import android.os.Bundle;
import android.view.Menu;
import android.widget.GridView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ieti.model.Product;
import com.ieti.persistence.ProductPersistence;
import com.ieti.persistence.ShopPersistence;
import com.ieti.persistence.impl.ProductPersistenceHttpImpl;
import com.ieti.persistence.impl.ProductPersistenceImpl;
import com.ieti.persistence.impl.ShopPersistenceHttpImpl;
import com.ieti.persistence.impl.ShopPersistenceImpl;
import com.ieti.ui.R;
import com.ieti.ui.products.cart.CartOption;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProductsActivity extends AppCompatActivity {

    private ProductPersistence productPersistence = ProductPersistenceHttpImpl.getInstance();
    private ShopPersistence shopPersistence = ShopPersistenceHttpImpl.getInstance();
    private Executor executor = Executors.newFixedThreadPool(1);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_view);
        GridView gridView = findViewById(R.id.idProducts_ViewGrid);
        String idShop = getIntent().getStringExtra("id");
        executor.execute(() -> {
            String nameShop = shopPersistence.getShopById(idShop).getName();
            List<Product> products = productPersistence.getProducts(idShop);
            runOnUiThread(() -> {
                getSupportActionBar().setTitle("Productos de " + nameShop);
                gridView.setAdapter(new ProductsAdapter(this, products));
            });
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        CartOption.onClickCart(menu, this);
        return true;
    }
}
