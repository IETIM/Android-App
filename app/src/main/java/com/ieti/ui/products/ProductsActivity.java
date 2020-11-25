package com.ieti.ui.products;
import android.os.Bundle;
import android.view.Menu;
import android.widget.GridView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.ieti.persistence.ProductPersistence;
import com.ieti.persistence.ShopPersistence;
import com.ieti.persistence.impl.ProductPersistenceImpl;
import com.ieti.persistence.impl.ShopPersistenceImpl;
import com.ieti.ui.R;
import com.ieti.ui.products.cart.CartOption;

public class ProductsActivity extends AppCompatActivity {

    private ProductPersistence productPersistence = ProductPersistenceImpl.getInstance();
    private ShopPersistence shopPersistence = ShopPersistenceImpl.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_view);
        GridView gridView = findViewById(R.id.idProducts_ViewGrid);
        String nameShop = getIntent().getStringExtra("id");
        getSupportActionBar().setTitle("Productos de " + shopPersistence.getShopById(nameShop).getName());
        gridView.setAdapter(new ProductsAdapter(this, productPersistence.getProducts(nameShop)));
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
