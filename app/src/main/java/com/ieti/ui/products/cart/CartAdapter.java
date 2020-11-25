package com.ieti.ui.products.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ieti.model.Product;
import com.ieti.model.Purchase;
import com.ieti.persistence.ProductPersistence;
import com.ieti.persistence.impl.CartPersistenceImpl;
import com.ieti.persistence.impl.ProductPersistenceHttpImpl;
import com.ieti.persistence.impl.ProductPersistenceImpl;
import com.ieti.ui.R;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CartAdapter extends BaseAdapter {

    private CartActivity context;
    private List<Purchase> purchases;
    private ProductPersistence productPersistence = ProductPersistenceHttpImpl.getInstance();
    private CartPersistenceImpl cartPersistenceImpl;
    private Executor executor = Executors.newFixedThreadPool(1);

    public CartAdapter(CartActivity context, CartPersistenceImpl cartPersistenceImpl) {
        this.context = context;
        this.cartPersistenceImpl = cartPersistenceImpl;
        this.purchases = cartPersistenceImpl.getPurchases();
    }

    @Override
    public int getCount() {
        return purchases.size();
    }

    @Override
    public Purchase getItem(int position) {
        return purchases.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Purchase purchase = getItem(position);
        View cardView = LayoutInflater.from(context).inflate(R.layout.item_cart, null).findViewById(R.id.idItem_CartCard);
        executor.execute(() -> {
            Product product = productPersistence.getProductById(purchase.getProductId());
            context.runOnUiThread(() -> {
                ((TextView)cardView.findViewById(R.id.idItem_CartNameProduct)).setText(product.getName());
                ((TextView)cardView.findViewById(R.id.idItem_CartPriceProduct)).setText(product.getPrice().toString());
                ((TextView)cardView.findViewById(R.id.idItem_CartDescriptionProduct)).setText(product.getDescription());
                cardView.findViewById(R.id.idItem_CartPlus).setOnClickListener((view) -> sumQuantity(view, cardView, purchase));
                cardView.findViewById(R.id.idItem_CartLess).setOnClickListener((view) -> substractQuantity(view, cardView, purchase));
                ((TextView)cardView.findViewById(R.id.idItem_CartNumber)).setText(purchase.getQuantity() + "");
            });
        });

        return cardView;
    }

    private void sumQuantity(View view, View cardView, Purchase purchase) {
        purchase.setQuantity(purchase.getQuantity() + 1);
        ((TextView)cardView.findViewById(R.id.idItem_CartNumber)).setText(purchase.getQuantity() + "");
    }

    private void substractQuantity(View view, View cardView, Purchase purchase) {
        purchase.setQuantity(purchase.getQuantity() - 1);
        if (purchase.getQuantity() == 0) {
            cartPersistenceImpl.deletePurchase(purchase);
            context.refreshView();
            return;
        }
        ((TextView)cardView.findViewById(R.id.idItem_CartNumber)).setText(purchase.getQuantity() + "");
    }
}
