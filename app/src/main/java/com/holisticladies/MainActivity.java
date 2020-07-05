package com.holisticladies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.holisticladies.fragments.BottomBarAdapter;
import com.holisticladies.fragments.CartFragment;
import com.holisticladies.fragments.FavouriteFragment;
import com.holisticladies.fragments.HomeFragment;
import com.holisticladies.fragments.MoreFragment;
import com.holisticladies.fragments.NoSwipePager;
import com.holisticladies.fragments.ProfileFragment;
import com.holisticladies.utils.BottomNavigationBehavior;

public class MainActivity extends AppCompatActivity {

    NoSwipePager viewPager;
    BottomNavigationView navigation;
    BottomBarAdapter pagerAdapter;

    ProfileFragment profile = new ProfileFragment();
    MoreFragment more = new MoreFragment();
    HomeFragment home = new HomeFragment();
    FavouriteFragment favourite = new FavouriteFragment();
    CartFragment cart = new CartFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);

        if (getSupportActionBar() == null){
            setSupportActionBar(toolbar);
        } else toolbar.setVisibility(View.GONE);

        getSupportActionBar().setTitle("Holistic Ladies");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().hide();

        navigation = findViewById(R.id.navigation);
        viewPager = findViewById(R.id.viewPager);

        //optimisation
        viewPager.setOffscreenPageLimit(5);
        viewPager.setPagingEnabled(false);

        pagerAdapter = new BottomBarAdapter(getSupportFragmentManager());

        pagerAdapter.addFragments(profile);
        pagerAdapter.addFragments(more);
        pagerAdapter.addFragments(home);
        pagerAdapter.addFragments(favourite);
        pagerAdapter.addFragments(cart);

        viewPager.setAdapter(pagerAdapter);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.navigationMyProfile:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.navigationMenu:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.navigationHome:
                        viewPager.setCurrentItem(2);
                        return true;
                    case R.id.navigationFavourite:
                        viewPager.setCurrentItem(3);
                        return true;
                    case R.id.navigationMyCart:
                        viewPager.setCurrentItem(4);
                        return true;

                }
                return false;
            }
        });


        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        navigation.setSelectedItemId(R.id.navigationHome);
    }

}