package com.ieti.ui.stores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ieti.model.Shop;
import com.ieti.ui.R;

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
        View card = LayoutInflater.from(context).inflate(R.layout.item_tienda,null).findViewById(R.id.card);
        ((TextView)card.findViewById(R.id.name)).setText(shop.getName());
        ((TextView)card.findViewById(R.id.desc)).setText(shop.getLocation());
        return card;
    }
}
