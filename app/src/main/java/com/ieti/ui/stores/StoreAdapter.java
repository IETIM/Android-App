package com.ieti.ui.stores;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ieti.model.Shop;
import com.ieti.ui.R;
import com.ieti.ui.products.ProductsActivity;

import java.net.URL;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class StoreAdapter extends BaseAdapter {

    private Context context;

    private Executor executor = Executors.newFixedThreadPool(1);

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
        executor.execute(()->{
            try{
                URL url = new URL(shop.getImage());
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                ((Activity) context).runOnUiThread(()->{
                    ((ImageView)((Activity) context).findViewById(R.id.img)).setImageBitmap(bmp);
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        return card;
    }

    public void redirectProducts(String idTienda) {
        Intent intent = new Intent(context, ProductsActivity.class);
        intent.putExtra("id", idTienda);
        context.startActivity(intent);
    }
}
