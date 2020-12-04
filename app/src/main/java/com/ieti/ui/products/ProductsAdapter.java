package com.ieti.ui.products;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.ieti.model.Product;
import com.ieti.model.Purchase;
import com.ieti.persistence.impl.CartPersistenceImpl;
import com.ieti.ui.R;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProductsAdapter extends BaseAdapter {

    private Activity activity;
    private List<Product> products;
    private CartPersistenceImpl cartPersistenceImpl = CartPersistenceImpl.getInstace();
    private Executor executor = Executors.newFixedThreadPool(1);

    public ProductsAdapter(Activity activity, List<Product> products) {
        this.activity = activity;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Product getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = getItem(position);
        View cardView = LayoutInflater.from(activity).inflate(R.layout.product_view, null).findViewById(R.id.idItem_CartCard);
        ((TextView)cardView.findViewById(R.id.idProduct_ViewNameProduct)).setText(product.getName());
        ((TextView)cardView.findViewById(R.id.idProduct_ViewPriceProduct)).setText(product.getPrice().toString());
        ((TextView)cardView.findViewById(R.id.idProduct_ViewDescriptionProduct)).setText(product.getDescription());
        cardView.findViewById(R.id.idProduct_ViewButtonCart).setOnClickListener((view) -> onClickAddCart(view, product));
        executor.execute(() -> {
            try {
                URL url = new URL(product.getImage());
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                activity.runOnUiThread(() -> {
                    ((ImageView)cardView.findViewById(R.id.idImageProductView)).setImageBitmap(bmp);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        });


        return cardView;
    }

    public void onClickAddCart(View view, Product product) {
        cartPersistenceImpl.addPurchase(new Purchase(product.getId(), 1));
        String message = "El producto " + product.getName() + " se ha añadido correctamente al carrito!";
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
