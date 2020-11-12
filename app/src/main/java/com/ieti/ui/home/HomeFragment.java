package com.ieti.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ieti.persistence.ShopPersistence;
import com.ieti.persistence.impl.ShopPersistenceImpl;
import com.ieti.ui.R;
import com.ieti.ui.stores.StoreActivity;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private ShopPersistence persistence = ShopPersistenceImpl.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        showCategories(root);
        return root;
    }

    public void showCategories(View root){
        List<String> categories = persistence.getCategories();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_list_item_1,categories);
        ((GridView) root.findViewById(R.id.layout)).setAdapter(adapter);
        ((GridView) root.findViewById(R.id.layout)).setOnItemClickListener((listView, view, position,id)->{
            changeView(categories.get(position));
        });

    }

    public void changeView(String category){
        Intent intent = new Intent(getContext(), StoreActivity.class);
        intent.putExtra("category",category);
        startActivity(intent);
    }
}