package com.holisticladies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Profile extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSelected(R.id.navigationMyProfile);
    }
}
