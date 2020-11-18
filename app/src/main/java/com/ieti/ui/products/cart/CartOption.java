package com.ieti.ui.products.cart;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

import com.ieti.ui.R;

public class CartOption {

    public static void onClickCart(Menu menu, Context context) {
        menu.findItem(R.id.idMainCart).setOnMenuItemClickListener((item) -> {
            Intent intent = new Intent(context, CartActivity.class);
            context.startActivity(intent);
            return true;
        });
    }

}
