package com.ieti.ui.stores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ieti.model.Shop;
import com.ieti.ui.R;
import com.ieti.ui.products.ProductsActivity;

import java.util.List;

public class StoreAdapter extends BaseAdapter {

    private Context context;

    private List<Shop> shops;

    public StoreAdapter(Context context,List<Shop> shops){
        this.context = context;
        this.shops = shops;
    }

    @Override
    public int getCount() {
        return shops.size();
    }

    @Override
    public Shop getItem(int position) {
        return shops.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Shop shop = getItem(position);
        View card = LayoutInflater.from(context).inflate(R.layout.item_tienda,null).findViewById(R.id.idItem_CartCard);
        ((TextView)card.findViewById(R.id.name)).setText(shop.getName());
        ((TextView)card.findViewById(R.id.desc)).setText(shop.getLocation());
        card.findViewById(R.id.idItem_TiendaVer).setOnClickListener((view) -> {
            redirectProducts(shop.getId());
        });
        return card;
    }

    public void redirectProducts(String idTienda) {
        Intent intent = new Intent(context, ProductsActivity.class);
        intent.putExtra("id", idTienda);
        context.startActivity(intent);
    }
}
