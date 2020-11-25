package com.ieti.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;
import com.ieti.map.service.Address;
import com.ieti.map.service.PlaceService;
import com.ieti.map.service.Places;
import com.ieti.model.Shop;
import com.ieti.persistence.ShopPersistence;
import com.ieti.persistence.impl.ShopPersistenceHttpImpl;
import com.ieti.persistence.impl.ShopPersistenceImpl;
import com.ieti.ui.products.ProductsActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private LocationManager manager;
    private Marker meMarker;
    private double longitude;
    private double latitude;
    private PlaceService placeService;
    private final ExecutorService executorService = Executors.newFixedThreadPool( 1 );
    private List<Marker> markers;
    private List<Shop> shops;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        markers= new ArrayList<Marker>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        placeService = new Retrofit.Builder().
                baseUrl("https://geocode.search.hereapi.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(PlaceService.class);
        getLocation();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getLocation() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0F, this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        executorService.execute(()->drawStores());
        mMap.setOnMarkerClickListener((marker)->clickMarker(marker));
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        System.out.println("--------------- Localiza ---------------");
        LatLng position = new LatLng(location.getLatitude(),location.getLongitude());
        System.out.println("lOC------------------"+location.getLatitude()+" "+location.getLongitude());
        if(meMarker==null){
            MarkerOptions a = new MarkerOptions()
                    .position(position);
            meMarker = mMap.addMarker(a);
            meMarker.setTitle("Yo");
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position,10));
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }
        if(Math.abs(longitude-location.getLongitude())>0.0001 || Math.abs(latitude-location.getLatitude())>0.0001){
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            meMarker.setPosition(position);
        }
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }
    @Override
     public void onStatusChanged(String provider,int status,Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    @Override
    public void onStop(){
        super.onStop();
        manager.removeUpdates(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRestart(){
        super.onRestart();
        getLocation();
    }

    public void drawStores(){
        executorService.execute(()->{
            shops = ShopPersistenceHttpImpl.getInstance().getShops();
            for(Shop shop:shops){
                LatLng latLng = getLocationShop(shop);
                if(latLng!=null) {
                    runOnUiThread(()->{
                        MarkerOptions a = new MarkerOptions()
                                .position(latLng);
                        Marker shopMarker = mMap.addMarker(a);
                        markers.add(shopMarker);
                        shopMarker.setTitle(shop.getName());
                    });
                }
            }
        });
    }

    private LatLng getLocationShop(Shop shop) {
        try {
            Places places = placeService.getPlaces(shop.getLocation()).execute().body();
            Address add = places.getItems().get(0).getPosition();
            return new LatLng(add.getLat(),add.getLng());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private boolean clickMarker(Marker marker){
        int index = markers.indexOf(marker);
        if(index>=0 && !marker.equals(meMarker)){
            Shop shop = shops.get(index);
            showPopUpWindow(shop);
        }
        return true;
    }

    private void showPopUpWindow(Shop shop){
        LayoutInflater inflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.item_tienda_map, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow.showAtLocation(findViewById(R.id.map), Gravity.CENTER, 0, 0);
        ((ImageView) popupView.findViewById(R.id.img)).setImageResource(R.drawable.tienda);
        ((TextView)popupView.findViewById(R.id.name)).setText(shop.getName());
        ((TextView)popupView.findViewById(R.id.desc)).setText(shop.getLocation());
        ((MaterialButton)popupView.findViewById(R.id.idItem_TiendaVer)).setOnClickListener((view)->redirectToStore(shop));
        ((MaterialButton)popupView.findViewById(R.id.idItem_salir)).setOnClickListener((view)->popupWindow.dismiss());
    }

    private void redirectToStore(Shop shop){
        Intent intent = new Intent(this, ProductsActivity.class);
        intent.putExtra("id", shop.getId());
        startActivity(intent);
    }

}