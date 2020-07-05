package com.holisticladies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static android.content.Context.MODE_PRIVATE;

public class BaseActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int prevNav = getSelectedNav();
            int currentNav = item.getItemId();
            if (currentNav == prevNav)
                return false;
            switch (item.getItemId()) {
                case R.id.navigationHome:
                    Intent ii = new Intent(BaseActivity.this, MainActivity.class);
                    startActivity(ii);
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigationFavourite:
                    if (prevNav != R.id.navigationHome)
                        finish();
                    Intent ii2 = new Intent(BaseActivity.this, Favourite.class);
                    startActivity(ii2);
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigationMyCart:
                    if (prevNav != R.id.navigationHome)
                        finish();
                    Intent ii3 = new Intent(BaseActivity.this, Cart.class);
                    startActivity(ii3);
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigationMyProfile:
                    if (prevNav != R.id.navigationHome)
                        finish();
                    Intent ii4 = new Intent(BaseActivity.this, Profile.class);
                    startActivity(ii4);
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigationMenu:
                    if (prevNav != R.id.navigationHome)
                        finish();
                    Intent ii5 = new Intent(BaseActivity.this, MenuA.class);
                    startActivity(ii5);
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        }

    };

    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public View setContentLayout(int layoutID)
    {
        FrameLayout contentLayout = (FrameLayout) findViewById(R.id.content_layout);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(layoutID, contentLayout, true);
    }

    public void setSelected(int optionID)
    {
        navigationView.getMenu().findItem(optionID).setChecked(true);
        getSharedPreferences(getPackageName(), MODE_PRIVATE).edit().putInt("selectedNav",optionID).commit();
    }

    public int getSelectedNav()
    {
        return getSharedPreferences(getPackageName(), MODE_PRIVATE).getInt("selectedNav", R.id.navigationHome);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}
