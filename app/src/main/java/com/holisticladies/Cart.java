package com.holisticladies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.holisticladies.adapters.ProductAdapter;
import com.holisticladies.model.Products;
import com.holisticladies.utils.BottomNavigationBehavior;
import com.holisticladies.utils.MyDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    List<Products> productsList;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigationMenu:
                    return true;
                case R.id.navigationMyProfile:
                    return true;
                case R.id.navigationHome:
                    startActivity(new Intent(Cart.this, MainActivity.class));
                    finish();
                    return true;
                case  R.id.navigationFavourite:
                    return true;
                case  R.id.navigationMyCart:
                   // startActivity(new Intent(Cart.this, Cart.class));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        bottomNavigationView.setSelectedItemId(R.id.navigationMyCart);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(Cart.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(Cart.this, LinearLayoutManager.VERTICAL, 16));

        productsList = new ArrayList<>();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Cart.this, MainActivity.class));
        finish();
    }

    /*
    protected void setCartLayout(){
        LinearLayout layoutCartItems =  findViewById(R.id.layout_items);
        LinearLayout layoutCartPayments = findViewById(R.id.layout_payment);
        LinearLayout layoutCartNoItems = findViewById(R.id.layout_cart_empty);

        if(MainActivity.notificationCountCart >0){
            layoutCartNoItems.setVisibility(View.GONE);
            layoutCartItems.setVisibility(View.VISIBLE);
            layoutCartPayments.setVisibility(View.VISIBLE);
        }else {
            layoutCartNoItems.setVisibility(View.VISIBLE);
            layoutCartItems.setVisibility(View.GONE);
            layoutCartPayments.setVisibility(View.GONE);

            Button bStartShopping = findViewById(R.id.bAddNew);
            bStartShopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }*/
}
