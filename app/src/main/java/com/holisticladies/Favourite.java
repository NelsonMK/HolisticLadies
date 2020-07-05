package com.holisticladies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Favourite extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSelected(R.id.navigationFavourite);
    }
}
