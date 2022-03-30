package com.meass.a24user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
public class Distance extends AppCompatActivity {
EditText fromdistance,toistance;
Button distance1;
TextView texts;
    Double distance;
    FusedLocationProviderClient client1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Map Ride");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        fromdistance=findViewById(R.id.fromdistance);
        toistance=findViewById(R.id.toistance);
        distance1=findViewById(R.id.distance);
        texts=findViewById(R.id.texts);
        distance1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(fromdistance.getText().toString())||TextUtils.isEmpty(toistance.getText().toString())) {
                    Toasty.error(getApplicationContext(),"Give all information",Toasty.LENGTH_SHORT,true).show();
                    return;
                }
                else {
                    String locationAddress=fromdistance.getText().toString();
                    String locationAddress1=toistance.getText().toString();
                    Geocoder geocoder=new Geocoder(Distance.this, Locale.getDefault());
                    String result=null;
                    try {
                        List addressList=geocoder.getFromLocationName(locationAddress,1);
                        List addressList1=geocoder.getFromLocationName(locationAddress1,1);
                        if (addressList!=null &&addressList.size()>0&&addressList1!=null &&addressList1.size()>0) {
                            Address address=(Address)addressList.get(0);
                            Address address1=(Address)addressList1.get(0);
                            StringBuilder builder=new StringBuilder();
                            builder.append(address.getLatitude()).append("\n")
                                    .append(address.getLongitude());
                            result=builder.toString();
                            LatLng latLng=new LatLng(address.getLatitude(),address.getLongitude());
                            LatLng latLng2=new LatLng(address.getLatitude(),address.getLongitude());
                          //  Toast.makeText(getApplicationContext(), ""+result, Toast.LENGTH_SHORT).show();
                            LatLng sydney = new LatLng(address.getLatitude(),address.getLongitude());
                            LatLng Brisbane =new LatLng(address1.getLatitude(),address1.getLongitude());

                            distance = SphericalUtil.computeDistanceBetween(sydney, Brisbane);
                            texts.setText("Distance between Two Place  is \n " + String.format("%.2f", distance / 1000) + "km");
                           // Toast.makeText(getApplicationContext(), "Distance between Two Place  is \n " + String.format("%.2f", distance / 1000) + "km", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toasty.error(getApplicationContext(),"Donot Find A Location.",Toasty.LENGTH_SHORT,true).show();
                            return;
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

}