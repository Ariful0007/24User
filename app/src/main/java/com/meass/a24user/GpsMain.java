package com.meass.a24user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class GpsMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, new NearByHospitalActivity()).commit();
    }
}