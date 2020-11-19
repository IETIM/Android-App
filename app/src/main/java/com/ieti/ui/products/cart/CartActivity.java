package com.ieti.ui.products.cart;

import android.os.Bundle;
import android.view.Menu;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.ieti.persistence.impl.CartPersistenceImpl;
import com.ieti.ui.R;

public class CartActivity extends AppCompatActivity {

    private CartPersistenceImpl cartPersistenceImpl = CartPersistenceImpl.getInstace();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Carrito de Compras");
        refreshView();

    }

    public void refreshView() {
        if (cartPersistenceImpl.getPurchases().size() == 0) {
            setContentView(R.layout.activity_cart_empty);
            return;
        }
        setContentView(R.layout.activity_cart);
        GridView gridView = findViewById(R.id.idActivity_CartGrid);
        gridView.setAdapter(new CartAdapter(this, cartPersistenceImpl));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping_cart, menu);
        menu.findItem(R.id.idMenu_Shopping_CartDelete).setOnMenuItemClickListener((item) -> {
            cartPersistenceImpl.deletePurchases();
            refreshView();
            return true;
        });
        return true;
    }
}
