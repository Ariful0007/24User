package com.meass.a24user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.DateFormat;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.collect.Maps;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.android.SphericalUtil;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;


import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
String ride;
    EditText searchv;
    private GoogleMap mMap;
    View view;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    Location lastlocation;
    private Marker currentLocationmMarker = null;
    private static final int Permission_Request = 99;
    int PROXIMITY_RADIUS = 10000;
    private FusedLocationProviderClient fusedLocationProviderClient,clientmain;
    SearchView searchview;
TextView distance_map;
CardView dailyCheckCard;
//FusedLocationProviderClient client1;
EditText fromdistance,toistance;
    Button distance1;
    TextView texts;
    Double distance;
    FusedLocationProviderClient client1;
    TextView bike,car,cng;
    String carrate,cngrate,bikerate;
    Button makerule23s,makerules;
    EditText datee;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        distance_map=findViewById(R.id.distance_map);
        bike=findViewById(R.id.bike);
        firebaseAuth=FirebaseAuth.getInstance();
        car=findViewById(R.id.car);
        cng=findViewById(R.id.cng);
        try {
            ride=getIntent().getStringExtra("ride");
            carrate=getIntent().getStringExtra("car");
            cngrate=getIntent().getStringExtra("bus");
            bikerate=getIntent().getStringExtra("bike");
        }catch (Exception e) {
            ride=getIntent().getStringExtra("ride");
            carrate=getIntent().getStringExtra("car");
            cngrate=getIntent().getStringExtra("bus");
            bikerate=getIntent().getStringExtra("bike");
        }
       // Toast.makeText(this, ""+ride, Toast.LENGTH_SHORT).show();
        makerule23s=findViewById(R.id.makerule23s);
        makerule23s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(this, "gfh", Toast.LENGTH_SHORT).show();
                final Dialog mDialog = new Dialog(MapsActivity.this);


                //mDialog = new Dialog(HomeACTIVITY.this);
                mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //LayoutInflater factory = LayoutInflater.from(this);

                mDialog.setContentView(R.layout.dialouge2);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                FloatingActionButton dialogClose=(FloatingActionButton)mDialog.findViewById(R.id.dialogClose);
                TextView datess=(TextView)mDialog.findViewById(R.id.datess);
                datee=(EditText) mDialog.findViewById(R.id.minwi22t23h);
                EditText timess=(EditText)mDialog.findViewById(R.id.minwit23h66);
                TextView timeeeee=(TextView)mDialog.findViewById(R.id.timeeeee);
                timeeeee.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Get Current Time
                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR_OF_DAY);
                        mMinute = c.get(Calendar.MINUTE);

                        // Launch Time Picker Dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(MapsActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {
                                        timess.setVisibility(View.VISIBLE);
                                        timess.setText(hourOfDay + ":" + minute);
                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();
                    }
                });
firebaseFirestore=FirebaseFirestore.getInstance();
firebaseAuth=FirebaseAuth.getInstance();
                datess.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);


                        DatePickerDialog datePickerDialog = new DatePickerDialog(MapsActivity.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        datee.setVisibility(View.VISIBLE);

                                        datee.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });

                //final View deleteDialogView = factory.inflate(R.layout.dialog_contact, null);
                //  mDialog.setContentView(deleteDialogView);
                EditText methodename=(EditText)mDialog.findViewById(R.id.methodename);

                EditText minwith=(EditText)mDialog.findViewById(R.id.minwith);
                EditText distance2=(EditText)mDialog.findViewById(R.id.distance);
                EditText minwit23h=(EditText)mDialog.findViewById(R.id.minwit23h);

                Button login_button=(Button)mDialog.findViewById(R.id.login_button);
                login_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // Toast.makeText(MapsActivity.this, "fghfgh", Toast.LENGTH_SHORT).show();
                        String fromdistance6=methodename.getText().toString();
                        String todiatance=minwith.getText().toString();
                        String diatance6=distance2.getText().toString();
                        String phone=minwit23h.getText().toString();
                        if (TextUtils.isEmpty(fromdistance6)||TextUtils.isEmpty(todiatance)||
                                TextUtils.isEmpty(diatance6)||
                                TextUtils.isEmpty(phone)||TextUtils.isEmpty(datee.getText())||
                        TextUtils.isEmpty(timess.getText().toString())) {
                            Toasty.error(getApplicationContext(),"Give all information",Toasty.LENGTH_SHORT,true).show();
                            return;
                        }
                        else {
                            AlertDialog.Builder builders=new AlertDialog.Builder(MapsActivity.this);
                            builders.setTitle("Conformation")
                                    .setMessage("Are you want to give pickpu request.")
                                    .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();

                                        }
                                    }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    String options[]={"Car","Bike","CNG"};
                                    AlertDialog.Builder builder=new AlertDialog.Builder(MapsActivity.this);
                                    builder.setTitle("Select Vechiles")
                                            .setItems(options, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                    if (i==0) {
                                                        final KProgressHUD progressDialog=  KProgressHUD.create(MapsActivity.this)
                                                                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                                                .setLabel("Uploading Data.....")
                                                                .setCancellable(false)
                                                                .setAnimationSpeed(2)
                                                                .setDimAmount(0.5f)
                                                                .show();
                                                        Long tsLong = System.currentTimeMillis()/1000;
                                                        String  ts = tsLong.toString();
                                                        String uuid= UUID.randomUUID().toString();
                                                        OrderModel orderModel=new OrderModel(fromdistance6,todiatance,diatance6,datee.getText().toString(),
                                                                "Pending",ts,uuid,phone,"Car","",firebaseAuth.getCurrentUser().getEmail(),"Pending Ride","Pending Ride",
                                                                "Custom Ride","Custom Ride","Custom Ride");
                                                        ///
                                                        firebaseFirestore.collection("MyRide")
                                                                .document(firebaseAuth.getCurrentUser().getEmail())
                                                                .collection("list")
                                                                .document(uuid)
                                                                .set(orderModel)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            firebaseFirestore.collection("AdminRequest")
                                                                                    .document(uuid)
                                                                                    .set(orderModel)
                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            if (task.isSuccessful()) {
                                                                                                progressDialog.dismiss();
                                                                                                new AestheticDialog.Builder(MapsActivity.this, DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                                        .setTitle("Conformation")
                                                                                                        .setMessage("Pickup request send to admin..\nNow admin will conact with you.")
                                                                                                        .setAnimation(DialogAnimation.SPLIT)
                                                                                                        .setOnClickListener(new OnDialogClickListener() {
                                                                                                            @Override
                                                                                                            public void onClick(AestheticDialog.Builder builder) {
                                                                                                                builder.dismiss();
                                                                                                                startActivity(new Intent(getApplicationContext(),HomeACTIVITY.class));

                                                                                                            }
                                                                                                        }).show();
                                                                                            }
                                                                                        }
                                                                                    });
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                    else if (i==1) {
                                                        final KProgressHUD progressDialog=  KProgressHUD.create(MapsActivity.this)
                                                                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                                                .setLabel("Uploading Data.....")
                                                                .setCancellable(false)
                                                                .setAnimationSpeed(2)
                                                                .setDimAmount(0.5f)
                                                                .show();
                                                        Long tsLong = System.currentTimeMillis()/1000;
                                                        String  ts = tsLong.toString();
                                                        String uuid= UUID.randomUUID().toString();
                                                        OrderModel orderModel=new OrderModel(fromdistance6,todiatance,diatance6,datee.getText().toString(),
                                                                "Pending",ts,uuid,phone,"Bike","",firebaseAuth.getCurrentUser().getEmail(),"Pending Ride","Pending Ride","Custom Ride","Custom Ride","Custom Ride");
                                                        ///
                                                        firebaseFirestore.collection("MyRide")
                                                                .document(firebaseAuth.getCurrentUser().getEmail())
                                                                .collection("list")
                                                                .document(uuid)
                                                                .set(orderModel)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            firebaseFirestore.collection("AdminRequest")
                                                                                    .document(uuid)
                                                                                    .set(orderModel)
                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            if (task.isSuccessful()) {
                                                                                                progressDialog.dismiss();
                                                                                                new AestheticDialog.Builder(MapsActivity.this, DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                                        .setTitle("Conformation")
                                                                                                        .setMessage("Pickup request send to admin..\nNow admin will conact with you.")
                                                                                                        .setAnimation(DialogAnimation.SPLIT)
                                                                                                        .setOnClickListener(new OnDialogClickListener() {
                                                                                                            @Override
                                                                                                            public void onClick(AestheticDialog.Builder builder) {
                                                                                                                builder.dismiss();
                                                                                                                startActivity(new Intent(getApplicationContext(),HomeACTIVITY.class));

                                                                                                            }
                                                                                                        }).show();
                                                                                            }
                                                                                        }
                                                                                    });
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                    else if (i==2) {
                                                        final KProgressHUD progressDialog=  KProgressHUD.create(MapsActivity.this)
                                                                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                                                .setLabel("Uploading Data.....")
                                                                .setCancellable(false)
                                                                .setAnimationSpeed(2)
                                                                .setDimAmount(0.5f)
                                                                .show();
                                                        Long tsLong = System.currentTimeMillis()/1000;
                                                        String  ts = tsLong.toString();
                                                        String uuid= UUID.randomUUID().toString();
                                                        OrderModel orderModel=new OrderModel(fromdistance6,todiatance,diatance6,datee.getText().toString(),
                                                                "Pending",ts,uuid,phone,"CNG","",firebaseAuth.getCurrentUser().getEmail(),"Pending Ride","Pending Ride"
                                                        ,"Custom Ride","Custom Ride","Custom Ride");
                                                        ///
                                                        firebaseFirestore.collection("MyRide")
                                                                .document(firebaseAuth.getCurrentUser().getEmail())
                                                                .collection("list")
                                                                .document(uuid)
                                                                .set(orderModel)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            firebaseFirestore.collection("AdminRequest")
                                                                                    .document(uuid)
                                                                                    .set(orderModel)
                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            if (task.isSuccessful()) {
                                                                                                progressDialog.dismiss();
                                                                                                new AestheticDialog.Builder(MapsActivity.this, DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                                        .setTitle("Conformation")
                                                                                                        .setMessage("Pickup request send to admin..\nNow admin will conact with you.")
                                                                                                        .setAnimation(DialogAnimation.SPLIT)
                                                                                                        .setOnClickListener(new OnDialogClickListener() {
                                                                                                            @Override
                                                                                                            public void onClick(AestheticDialog.Builder builder) {
                                                                                                                builder.dismiss();
                                                                                                                startActivity(new Intent(getApplicationContext(),HomeACTIVITY.class));

                                                                                                            }
                                                                                                        }).show();
                                                                                            }
                                                                                        }
                                                                                    });
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                }
                                            }).create().show();
                                }
                            }).create().show();

                        }
                    }
                });
/*
 mDialog.setContentView(R.layout.dialouge2);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView datess=(TextView)mDialog.findViewById(R.id.datess);
                datess.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MapsActivity.this, "fdgdfg", Toast.LENGTH_SHORT).show();
                    }
                });
                 datee=(TextView)mDialog.findViewById(R.id.minwi22t23h);
                datee.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Get Current Date
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);


                        DatePickerDialog datePickerDialog = new DatePickerDialog(MapsActivity.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {

                                        datee.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });
 */
                dialogClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                mDialog.show();
            }
        });
         /*
         dailyCheckCard=findViewById(R.id.dailyCheckCard);

        dailyCheckCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MapsActivity.this);
                builder.setTitle("Set Up Order")
                        .setMessage("Are you want to go your destination using bike")
                        .setPositiveButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).setNegativeButton("Next Step", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(getApplicationContext(),SecondStep.class);
                        intent.putExtra("startlocation",fromdistance.getText());
                        intent.putExtra("endlocation",toistance.getText());
                        intent.putExtra("distance",""+distance);
                        intent.putExtra("price",""+mainbike);
                        startActivity(intent);
                    }
                });
            }
        });
          */
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mapFragment.getMapAsync(MapsActivity.this);
        client1=LocationServices.getFusedLocationProviderClient(this);
        //
        //make
        makerules=findViewById(R.id.makerules);
        makerules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(fromdistance.getText().toString())||TextUtils.isEmpty(toistance.getText().toString())) {
               Toasty.info(getApplicationContext(),"Search a location",Toasty.LENGTH_SHORT,true).show();
                }
                else {
                    // Toast.makeText(this, "gfh", Toast.LENGTH_SHORT).show();
                    final Dialog mDialog = new Dialog(MapsActivity.this);


                    //mDialog = new Dialog(HomeACTIVITY.this);
                    mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    //LayoutInflater factory = LayoutInflater.from(this);

                    mDialog.setContentView(R.layout.dialouge2);
                    mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    FloatingActionButton dialogClose=(FloatingActionButton)mDialog.findViewById(R.id.dialogClose);
                    TextView datess=(TextView)mDialog.findViewById(R.id.datess);
                    datee=(EditText) mDialog.findViewById(R.id.minwi22t23h);
                    EditText timess=(EditText)mDialog.findViewById(R.id.minwit23h66);
                    TextView timeeeee=(TextView)mDialog.findViewById(R.id.timeeeee);
                    timeeeee.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Get Current Time
                            final Calendar c = Calendar.getInstance();
                            mHour = c.get(Calendar.HOUR_OF_DAY);
                            mMinute = c.get(Calendar.MINUTE);

                            // Launch Time Picker Dialog
                            TimePickerDialog timePickerDialog = new TimePickerDialog(MapsActivity.this,
                                    new TimePickerDialog.OnTimeSetListener() {

                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay,
                                                              int minute) {
                                            timess.setVisibility(View.VISIBLE);
                                            timess.setText(hourOfDay + ":" + minute);
                                        }
                                    }, mHour, mMinute, false);
                            timePickerDialog.show();
                        }
                    });
                    firebaseFirestore=FirebaseFirestore.getInstance();
                    firebaseAuth=FirebaseAuth.getInstance();
                    datess.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final Calendar c = Calendar.getInstance();
                            mYear = c.get(Calendar.YEAR);
                            mMonth = c.get(Calendar.MONTH);
                            mDay = c.get(Calendar.DAY_OF_MONTH);


                            DatePickerDialog datePickerDialog = new DatePickerDialog(MapsActivity.this,
                                    new DatePickerDialog.OnDateSetListener() {

                                        @Override
                                        public void onDateSet(DatePicker view, int year,
                                                              int monthOfYear, int dayOfMonth) {
                                            datee.setVisibility(View.VISIBLE);

                                            datee.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                        }
                                    }, mYear, mMonth, mDay);
                            datePickerDialog.show();
                        }
                    });

                    //final View deleteDialogView = factory.inflate(R.layout.dialog_contact, null);
                    //  mDialog.setContentView(deleteDialogView);
                    EditText methodename=(EditText)mDialog.findViewById(R.id.methodename);
                    methodename.setText(fromdistance.getText().toString());

                    EditText minwith=(EditText)mDialog.findViewById(R.id.minwith);
                    minwith.setText(toistance.getText().toString());
                    EditText distance2=(EditText)mDialog.findViewById(R.id.distance);
                    distance2.setText(""+String.format("%.2f", distance / 1000) + "km");
                    EditText minwit23h=(EditText)mDialog.findViewById(R.id.minwit23h);

                    Button login_button=(Button)mDialog.findViewById(R.id.login_button);
                    login_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Toast.makeText(MapsActivity.this, "fghfgh", Toast.LENGTH_SHORT).show();
                            String fromdistance6=methodename.getText().toString();
                            String todiatance=minwith.getText().toString();
                            String diatance6=distance2.getText().toString();
                            String phone=minwit23h.getText().toString();
                            if (TextUtils.isEmpty(fromdistance6)||TextUtils.isEmpty(todiatance)||
                                    TextUtils.isEmpty(diatance6)||
                                    TextUtils.isEmpty(phone)||TextUtils.isEmpty(datee.getText())||
                                    TextUtils.isEmpty(timess.getText().toString())) {
                                Toasty.error(getApplicationContext(),"Give all information",Toasty.LENGTH_SHORT,true).show();
                                return;
                            }
                            else {
                                AlertDialog.Builder builders=new AlertDialog.Builder(MapsActivity.this);
                                builders.setTitle("Conformation")
                                        .setMessage("Are you want to give pickpu request.")
                                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();

                                            }
                                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        String options[]={"Car","Bike","CNG"};
                                        AlertDialog.Builder builder=new AlertDialog.Builder(MapsActivity.this);
                                        builder.setTitle("Select Vechiles")
                                                .setItems(options, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.dismiss();
                                                        if (i==0) {
                                                            AlertDialog.Builder builder1=new AlertDialog.Builder(MapsActivity.this);
                                                            String options[]={"Hand Cash","Nogod","Bkash"};
                                                            builder1.setTitle("Payment Option")
                                                                    .setItems(options, new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                                            if (i==0) {
                                                                                dialogInterface.dismiss();
                                                                                final KProgressHUD progressDialog=  KProgressHUD.create(MapsActivity.this)
                                                                                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                                                                        .setLabel("Uploading Data.....")
                                                                                        .setCancellable(false)
                                                                                        .setAnimationSpeed(2)
                                                                                        .setDimAmount(0.5f)
                                                                                        .show();
                                                                                Long tsLong = System.currentTimeMillis()/1000;
                                                                                String  ts = tsLong.toString();
                                                                                String uuid= UUID.randomUUID().toString();
                                                                                double maindistance=distance / 1000;
                                                                                // mainbike=maindistance*(Double.parseDouble(bikerate));
                                                                                // bike.setText("BIKE\n"+String.format("%.2f", mainbike) + "Tk");

                                                                                maincar=maindistance*(Double.parseDouble(carrate));
                                                                                //   car.setText("CAR\n"+String.format("%.2f", maincar) + "Tk");
                                                                                OrderModel orderModel=new OrderModel(fromdistance6,todiatance,diatance6,datee.getText().toString(),
                                                                                        "Pending",ts,uuid,phone,"Car",String.format("%.2f", maincar) + "Tk",firebaseAuth.getCurrentUser().getEmail(),"Pending Ride","Pending Ride"
                                                                                        ,"Hand Cash","",ride);
                                                                                ///
                                                                                firebaseFirestore.collection("MyRide")
                                                                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                        .collection("list")
                                                                                        .document(uuid)
                                                                                        .set(orderModel)
                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    firebaseFirestore.collection("AdminRequest")
                                                                                                            .document(uuid)
                                                                                                            .set(orderModel)
                                                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                @Override
                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                    if (task.isSuccessful()) {
                                                                                                                        progressDialog.dismiss();

                                                                                                                        new AestheticDialog.Builder(MapsActivity.this, DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                                                                .setTitle("Conformation")
                                                                                                                                .setMessage("Pickup request send to admin..\nNow admin will conact with you.")
                                                                                                                                .setAnimation(DialogAnimation.SPLIT)
                                                                                                                                .setOnClickListener(new OnDialogClickListener() {
                                                                                                                                    @Override
                                                                                                                                    public void onClick(AestheticDialog.Builder builder) {
                                                                                                                                        builder.dismiss();
                                                                                                                                        startActivity(new Intent(getApplicationContext(),HomeACTIVITY.class));

                                                                                                                                    }
                                                                                                                                }).show();
                                                                                                                    }
                                                                                                                }
                                                                                                            });
                                                                                                }
                                                                                            }
                                                                                        });
                                                                            }
                                                                            else if (i==1) {

                                                                                firebaseFirestore.collection("Payment__number")
                                                                                        .document("nogod@gmail.com")
                                                                                        .get()
                                                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    if (task.getResult().exists()) {
                                                                                                        final EditText input = new EditText(MapsActivity.this);
                                                                                                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                                                                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                                                                                LinearLayout.LayoutParams.MATCH_PARENT);
                                                                                                        input.setLayoutParams(lp);
                                                                                                        input.setGravity(Gravity.CENTER);
                                                                                                        input.setHint("Enter Phonenumber");

                                                                                                        new androidx.appcompat.app.AlertDialog.Builder(MapsActivity.this)
                                                                                                                .setTitle("Payment Number ")
                                                                                                                .setMessage("Send Payment This Number : \nNogod Personal : "+task.getResult().getString("number"))
                                                                                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                                                                    @Override
                                                                                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                                                                                        if (TextUtils.isEmpty(input.getText().toString()))
                                                                                                                        {
                                                                                                                            Toast.makeText(MapsActivity.this, "Enter Values", Toast.LENGTH_SHORT).show();
                                                                                                                        }
                                                                                                                        else {
                                                                                                                            dialogInterface.dismiss();
                                                                                                                            final KProgressHUD progressDialog=  KProgressHUD.create(MapsActivity.this)
                                                                                                                                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                                                                                                                    .setLabel("Uploading Data.....")
                                                                                                                                    .setCancellable(false)
                                                                                                                                    .setAnimationSpeed(2)
                                                                                                                                    .setDimAmount(0.5f)
                                                                                                                                    .show();
                                                                                                                            Long tsLong = System.currentTimeMillis()/1000;
                                                                                                                            String  ts = tsLong.toString();
                                                                                                                            String uuid= UUID.randomUUID().toString();
                                                                                                                            double maindistance=distance / 1000;
                                                                                                                            // mainbike=maindistance*(Double.parseDouble(bikerate));
                                                                                                                            // bike.setText("BIKE\n"+String.format("%.2f", mainbike) + "Tk");

                                                                                                                            maincar=maindistance*(Double.parseDouble(carrate));
                                                                                                                            //   car.setText("CAR\n"+String.format("%.2f", maincar) + "Tk");
                                                                                                                            OrderModel orderModel=new OrderModel(fromdistance6,todiatance,diatance6,datee.getText().toString(),
                                                                                                                                    "Pending",ts,uuid,phone,"Car",String.format("%.2f", maincar) + "Tk",firebaseAuth.getCurrentUser().getEmail(),"Pending Ride","Pending Ride"
                                                                                                                                    ,"Nogod",input.getText().toString(),ride);
                                                                                                                            ///
                                                                                                                            Calendar calendar=Calendar.getInstance();
                                                                                                                            final  int year=calendar.get(Calendar.YEAR);
                                                                                                                            final int month=calendar.get(Calendar.MONTH);
                                                                                                                            final int day=calendar.get(Calendar.DAY_OF_MONTH);
                                                                                                                            firebaseFirestore.collection("Payment")
                                                                                                                                    .document(""+year)
                                                                                                                                    .collection(""+month)
                                                                                                                                    .document(""+day)
                                                                                                                                    .collection("List")
                                                                                                                                    .document(uuid)
                                                                                                                                    .set(orderModel)
                                                                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                        @Override
                                                                                                                                        public void onComplete(@NonNull Task<Void> task) {

                                                                                                                                        }
                                                                                                                                    });
                                                                                                                            firebaseFirestore.collection("MyRide")
                                                                                                                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                                    .collection("list")
                                                                                                                                    .document(uuid)
                                                                                                                                    .set(orderModel)
                                                                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                        @Override
                                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                            if (task.isSuccessful()) {
                                                                                                                                                firebaseFirestore.collection("AdminRequest")
                                                                                                                                                        .document(uuid)
                                                                                                                                                        .set(orderModel)
                                                                                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                            @Override
                                                                                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                                if (task.isSuccessful()) {
                                                                                                                                                                    progressDialog.dismiss();

                                                                                                                                                                    new AestheticDialog.Builder(MapsActivity.this, DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                                                                                                            .setTitle("Conformation")
                                                                                                                                                                            .setMessage("Pickup request send to admin..\nNow admin will conact with you.")
                                                                                                                                                                            .setAnimation(DialogAnimation.SPLIT)
                                                                                                                                                                            .setOnClickListener(new OnDialogClickListener() {
                                                                                                                                                                                @Override
                                                                                                                                                                                public void onClick(AestheticDialog.Builder builder) {
                                                                                                                                                                                    builder.dismiss();
                                                                                                                                                                                    startActivity(new Intent(getApplicationContext(),HomeACTIVITY.class));

                                                                                                                                                                                }
                                                                                                                                                                            }).show();
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        });
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    });

                                                                                                                        }
                                                                                                                    }
                                                                                                                }). setIcon(R.drawable.neww__logo)
                                                                                                                .setView(input)
                                                                                                                .show();
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        });

                                                                            }
                                                                            //
                                                                            else if (i==2) {
                                                                                firebaseFirestore.collection("Payment__number")
                                                                                        .document("bkash@gmail.com")
                                                                                        .get()
                                                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    if (task.getResult().exists()) {
                                                                                                        final EditText input = new EditText(MapsActivity.this);
                                                                                                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                                                                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                                                                                LinearLayout.LayoutParams.MATCH_PARENT);
                                                                                                        input.setLayoutParams(lp);
                                                                                                        input.setGravity(Gravity.CENTER);
                                                                                                        input.setHint("Enter Phonenumber");

                                                                                                        new androidx.appcompat.app.AlertDialog.Builder(MapsActivity.this)
                                                                                                                .setTitle("Payment Number ")
                                                                                                                .setMessage("Send Payment This Number : \nBkash Personal : "+task.getResult().getString("number"))
                                                                                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                                                                    @Override
                                                                                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                                                                                        if (TextUtils.isEmpty(input.getText().toString()))
                                                                                                                        {
                                                                                                                            Toast.makeText(MapsActivity.this, "Enter Values", Toast.LENGTH_SHORT).show();
                                                                                                                        }
                                                                                                                        else {
                                                                                                                            dialogInterface.dismiss();
                                                                                                                            final KProgressHUD progressDialog=  KProgressHUD.create(MapsActivity.this)
                                                                                                                                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                                                                                                                    .setLabel("Uploading Data.....")
                                                                                                                                    .setCancellable(false)
                                                                                                                                    .setAnimationSpeed(2)
                                                                                                                                    .setDimAmount(0.5f)
                                                                                                                                    .show();
                                                                                                                            Long tsLong = System.currentTimeMillis()/1000;
                                                                                                                            String  ts = tsLong.toString();
                                                                                                                            String uuid= UUID.randomUUID().toString();
                                                                                                                            double maindistance=distance / 1000;
                                                                                                                            // mainbike=maindistance*(Double.parseDouble(bikerate));
                                                                                                                            // bike.setText("BIKE\n"+String.format("%.2f", mainbike) + "Tk");

                                                                                                                            maincar=maindistance*(Double.parseDouble(carrate));
                                                                                                                            //   car.setText("CAR\n"+String.format("%.2f", maincar) + "Tk");
                                                                                                                            OrderModel orderModel=new OrderModel(fromdistance6,todiatance,diatance6,datee.getText().toString(),
                                                                                                                                    "Pending",ts,uuid,phone,"Car",String.format("%.2f", maincar) + "Tk",firebaseAuth.getCurrentUser().getEmail(),"Pending Ride","Pending Ride"
                                                                                                                                    ,"Bkash",input.getText().toString(),ride);
                                                                                                                            ///
                                                                                                                            Calendar calendar=Calendar.getInstance();
                                                                                                                            final  int year=calendar.get(Calendar.YEAR);
                                                                                                                            final int month=calendar.get(Calendar.MONTH);
                                                                                                                            final int day=calendar.get(Calendar.DAY_OF_MONTH);
                                                                                                                            firebaseFirestore.collection("Payment")
                                                                                                                                    .document(""+year)
                                                                                                                                    .collection(""+month)
                                                                                                                                    .document(""+day)
                                                                                                                                    .collection("List")
                                                                                                                                    .document(uuid)
                                                                                                                                    .set(orderModel)
                                                                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                        @Override
                                                                                                                                        public void onComplete(@NonNull Task<Void> task) {

                                                                                                                                        }
                                                                                                                                    });
                                                                                                                            firebaseFirestore.collection("MyRide")
                                                                                                                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                                    .collection("list")
                                                                                                                                    .document(uuid)
                                                                                                                                    .set(orderModel)
                                                                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                        @Override
                                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                            if (task.isSuccessful()) {
                                                                                                                                                firebaseFirestore.collection("AdminRequest")
                                                                                                                                                        .document(uuid)
                                                                                                                                                        .set(orderModel)
                                                                                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                            @Override
                                                                                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                                if (task.isSuccessful()) {
                                                                                                                                                                    progressDialog.dismiss();

                                                                                                                                                                    new AestheticDialog.Builder(MapsActivity.this, DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                                                                                                            .setTitle("Conformation")
                                                                                                                                                                            .setMessage("Pickup request send to admin..\nNow admin will conact with you.")
                                                                                                                                                                            .setAnimation(DialogAnimation.SPLIT)
                                                                                                                                                                            .setOnClickListener(new OnDialogClickListener() {
                                                                                                                                                                                @Override
                                                                                                                                                                                public void onClick(AestheticDialog.Builder builder) {
                                                                                                                                                                                    builder.dismiss();
                                                                                                                                                                                    startActivity(new Intent(getApplicationContext(),HomeACTIVITY.class));

                                                                                                                                                                                }
                                                                                                                                                                            }).show();
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        });
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    });

                                                                                                                        }
                                                                                                                    }
                                                                                                                }). setIcon(R.drawable.neww__logo)
                                                                                                                .setView(input)
                                                                                                                .show();
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        });

                                                                            }

                                                                        }
                                                                    }).create().show();

                                                        }
                                                        else if (i==1) {

                                                            AlertDialog.Builder builder1=new AlertDialog.Builder(MapsActivity.this);
                                                            String options[]={"Hand Cash","Nogod","Bkash"};
                                                            builder1.setTitle("Payment Option")
                                                                    .setItems(options, new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                                            if (i==0) {
                                                                                dialogInterface.dismiss();
                                                                                final KProgressHUD progressDialog=  KProgressHUD.create(MapsActivity.this)
                                                                                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                                                                        .setLabel("Uploading Data.....")
                                                                                        .setCancellable(false)
                                                                                        .setAnimationSpeed(2)
                                                                                        .setDimAmount(0.5f)
                                                                                        .show();
                                                                                double maindistance=distance / 1000;
                                                                                Long tsLong = System.currentTimeMillis()/1000;
                                                                                String  ts = tsLong.toString();
                                                                                String uuid= UUID.randomUUID().toString();

                                                                                mainbike=maindistance*(Double.parseDouble(bikerate));
                                                                                // bike.setText("BIKE\n"+String.format("%.2f", mainbike) + "Tk");
                                                                                maincar=maindistance*(Double.parseDouble(carrate));
                                                                                //   car.setText("CAR\n"+String.format("%.2f", maincar) + "Tk");
                                                                                // dailyCheckCard.setClickable(true);
                                                                                OrderModel orderModel=new OrderModel(fromdistance6,todiatance,diatance6,datee.getText().toString(),
                                                                                        "Pending",ts,uuid,phone,"Bike",String.format("%.2f", mainbike) + "Tk",firebaseAuth.getCurrentUser().getEmail(),"Pending Ride","Pending Ride",
                                                                                        "Hand Cash","Hand Cash",ride);
                                                                                ///
                                                                                firebaseFirestore.collection("MyRide")
                                                                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                        .collection("list")
                                                                                        .document(uuid)
                                                                                        .set(orderModel)
                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    firebaseFirestore.collection("AdminRequest")
                                                                                                            .document(uuid)
                                                                                                            .set(orderModel)
                                                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                @Override
                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                    if (task.isSuccessful()) {
                                                                                                                        progressDialog.dismiss();

                                                                                                                        new AestheticDialog.Builder(MapsActivity.this, DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                                                                .setTitle("Conformation")
                                                                                                                                .setMessage("Pickup request send to admin..\nNow admin will conact with you.")
                                                                                                                                .setAnimation(DialogAnimation.SPLIT)
                                                                                                                                .setOnClickListener(new OnDialogClickListener() {
                                                                                                                                    @Override
                                                                                                                                    public void onClick(AestheticDialog.Builder builder) {
                                                                                                                                        builder.dismiss();
                                                                                                                                        startActivity(new Intent(getApplicationContext(),HomeACTIVITY.class));

                                                                                                                                    }
                                                                                                                                }).show();
                                                                                                                    }
                                                                                                                }
                                                                                                            });
                                                                                                }
                                                                                            }
                                                                                        });
                                                                            }
                                                                            else if (i==1) {
                                                                                firebaseFirestore.collection("Payment__number")
                                                                                        .document("nogod@gmail.com")
                                                                                        .get()
                                                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    if (task.getResult().exists()) {
                                                                                                        final EditText input = new EditText(MapsActivity.this);
                                                                                                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                                                                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                                                                                LinearLayout.LayoutParams.MATCH_PARENT);
                                                                                                        input.setLayoutParams(lp);
                                                                                                        input.setGravity(Gravity.CENTER);
                                                                                                        input.setHint("Enter Phonenumber");

                                                                                                        new androidx.appcompat.app.AlertDialog.Builder(MapsActivity.this)
                                                                                                                .setTitle("Payment Number ")
                                                                                                                .setMessage("Send Payment This Number : \nNogod Personal : "+task.getResult().getString("number"))
                                                                                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                                                                    @Override
                                                                                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                                                                                        if (TextUtils.isEmpty(input.getText().toString()))
                                                                                                                        {
                                                                                                                            Toast.makeText(MapsActivity.this, "Enter Values", Toast.LENGTH_SHORT).show();
                                                                                                                        }
                                                                                                                        else {
                                                                                                                            dialogInterface.dismiss();
                                                                                                                            final KProgressHUD progressDialog=  KProgressHUD.create(MapsActivity.this)
                                                                                                                                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                                                                                                                    .setLabel("Uploading Data.....")
                                                                                                                                    .setCancellable(false)
                                                                                                                                    .setAnimationSpeed(2)
                                                                                                                                    .setDimAmount(0.5f)
                                                                                                                                    .show();
                                                                                                                            double maindistance=distance / 1000;
                                                                                                                            Long tsLong = System.currentTimeMillis()/1000;
                                                                                                                            String  ts = tsLong.toString();
                                                                                                                            String uuid= UUID.randomUUID().toString();

                                                                                                                            mainbike=maindistance*(Double.parseDouble(bikerate));
                                                                                                                            // bike.setText("BIKE\n"+String.format("%.2f", mainbike) + "Tk");
                                                                                                                            maincar=maindistance*(Double.parseDouble(carrate));
                                                                                                                            //   car.setText("CAR\n"+String.format("%.2f", maincar) + "Tk");
                                                                                                                            // dailyCheckCard.setClickable(true);
                                                                                                                            OrderModel orderModel=new OrderModel(fromdistance6,todiatance,diatance6,datee.getText().toString(),
                                                                                                                                    "Pending",ts,uuid,phone,"Bike",String.format("%.2f", mainbike) + "Tk",firebaseAuth.getCurrentUser().getEmail(),"Pending Ride","Pending Ride",
                                                                                                                                    "Bkash",input.getText().toString(),ride);
                                                                                                                            ///
                                                                                                                            Calendar calendar=Calendar.getInstance();
                                                                                                                            final  int year=calendar.get(Calendar.YEAR);
                                                                                                                            final int month=calendar.get(Calendar.MONTH);
                                                                                                                            final int day=calendar.get(Calendar.DAY_OF_MONTH);
                                                                                                                            firebaseFirestore.collection("Payment")
                                                                                                                                    .document(""+year)
                                                                                                                                    .collection(""+month)
                                                                                                                                    .document(""+day)
                                                                                                                                    .collection("List")
                                                                                                                                    .document(uuid)
                                                                                                                                    .set(orderModel)
                                                                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                        @Override
                                                                                                                                        public void onComplete(@NonNull Task<Void> task) {

                                                                                                                                        }
                                                                                                                                    });
                                                                                                                            firebaseFirestore.collection("MyRide")
                                                                                                                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                                    .collection("list")
                                                                                                                                    .document(uuid)
                                                                                                                                    .set(orderModel)
                                                                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                        @Override
                                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                            if (task.isSuccessful()) {
                                                                                                                                                firebaseFirestore.collection("AdminRequest")
                                                                                                                                                        .document(uuid)
                                                                                                                                                        .set(orderModel)
                                                                                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                            @Override
                                                                                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                                if (task.isSuccessful()) {
                                                                                                                                                                    progressDialog.dismiss();

                                                                                                                                                                    new AestheticDialog.Builder(MapsActivity.this, DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                                                                                                            .setTitle("Conformation")
                                                                                                                                                                            .setMessage("Pickup request send to admin..\nNow admin will conact with you.")
                                                                                                                                                                            .setAnimation(DialogAnimation.SPLIT)
                                                                                                                                                                            .setOnClickListener(new OnDialogClickListener() {
                                                                                                                                                                                @Override
                                                                                                                                                                                public void onClick(AestheticDialog.Builder builder) {
                                                                                                                                                                                    builder.dismiss();
                                                                                                                                                                                    startActivity(new Intent(getApplicationContext(),HomeACTIVITY.class));

                                                                                                                                                                                }
                                                                                                                                                                            }).show();
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        });
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    });

                                                                                                                        }
                                                                                                                    }
                                                                                                                }). setIcon(R.drawable.neww__logo)
                                                                                                                .setView(input)
                                                                                                                .show();
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        });

                                                                            }
                                                                            //
                                                                            else if (i==2) {
                                                                                firebaseFirestore.collection("Payment__number")
                                                                                        .document("bkash@gmail.com")
                                                                                        .get()
                                                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    if (task.getResult().exists()) {
                                                                                                        final EditText input = new EditText(MapsActivity.this);
                                                                                                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                                                                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                                                                                LinearLayout.LayoutParams.MATCH_PARENT);
                                                                                                        input.setLayoutParams(lp);
                                                                                                        input.setGravity(Gravity.CENTER);
                                                                                                        input.setHint("Enter Phonenumber");

                                                                                                        new androidx.appcompat.app.AlertDialog.Builder(MapsActivity.this)
                                                                                                                .setTitle("Payment Number ")
                                                                                                                .setMessage("Send Payment This Number : \nBkash Personal : "+task.getResult().getString("number"))
                                                                                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                                                                    @Override
                                                                                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                                                                                        if (TextUtils.isEmpty(input.getText().toString()))
                                                                                                                        {
                                                                                                                            Toast.makeText(MapsActivity.this, "Enter Values", Toast.LENGTH_SHORT).show();
                                                                                                                        }
                                                                                                                        else {
                                                                                                                            dialogInterface.dismiss();
                                                                                                                            final KProgressHUD progressDialog=  KProgressHUD.create(MapsActivity.this)
                                                                                                                                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                                                                                                                    .setLabel("Uploading Data.....")
                                                                                                                                    .setCancellable(false)
                                                                                                                                    .setAnimationSpeed(2)
                                                                                                                                    .setDimAmount(0.5f)
                                                                                                                                    .show();
                                                                                                                           ;
                                                                                                                            double maindistance=distance / 1000;
                                                                                                                            Long tsLong = System.currentTimeMillis()/1000;
                                                                                                                            String  ts = tsLong.toString();
                                                                                                                            String uuid= UUID.randomUUID().toString();

                                                                                                                            mainbike=maindistance*(Double.parseDouble(bikerate));
                                                                                                                            // bike.setText("BIKE\n"+String.format("%.2f", mainbike) + "Tk");
                                                                                                                            maincar=maindistance*(Double.parseDouble(carrate));
                                                                                                                            //   car.setText("CAR\n"+String.format("%.2f", maincar) + "Tk");
                                                                                                                            // dailyCheckCard.setClickable(true);
                                                                                                                            OrderModel orderModel=new OrderModel(fromdistance6,todiatance,diatance6,datee.getText().toString(),
                                                                                                                                    "Pending",ts,uuid,phone,"Bike",String.format("%.2f", mainbike) + "Tk",firebaseAuth.getCurrentUser().getEmail(),"Pending Ride","Pending Ride",
                                                                                                                                    "Bkash",input.getText().toString(),ride);
                                                                                                                            ///
                                                                                                                            Calendar calendar=Calendar.getInstance();
                                                                                                                            final  int year=calendar.get(Calendar.YEAR);
                                                                                                                            final int month=calendar.get(Calendar.MONTH);
                                                                                                                            final int day=calendar.get(Calendar.DAY_OF_MONTH);
                                                                                                                            firebaseFirestore.collection("Payment")
                                                                                                                                    .document(""+year)
                                                                                                                                    .collection(""+month)
                                                                                                                                    .document(""+day)
                                                                                                                                    .collection("List")
                                                                                                                                    .document(uuid)
                                                                                                                                    .set(orderModel)
                                                                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                        @Override
                                                                                                                                        public void onComplete(@NonNull Task<Void> task) {

                                                                                                                                        }
                                                                                                                                    });
                                                                                                                            firebaseFirestore.collection("MyRide")
                                                                                                                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                                    .collection("list")
                                                                                                                                    .document(uuid)
                                                                                                                                    .set(orderModel)
                                                                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                        @Override
                                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                            if (task.isSuccessful()) {
                                                                                                                                                firebaseFirestore.collection("AdminRequest")
                                                                                                                                                        .document(uuid)
                                                                                                                                                        .set(orderModel)
                                                                                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                            @Override
                                                                                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                                if (task.isSuccessful()) {
                                                                                                                                                                    progressDialog.dismiss();

                                                                                                                                                                    new AestheticDialog.Builder(MapsActivity.this, DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                                                                                                            .setTitle("Conformation")
                                                                                                                                                                            .setMessage("Pickup request send to admin..\nNow admin will conact with you.")
                                                                                                                                                                            .setAnimation(DialogAnimation.SPLIT)
                                                                                                                                                                            .setOnClickListener(new OnDialogClickListener() {
                                                                                                                                                                                @Override
                                                                                                                                                                                public void onClick(AestheticDialog.Builder builder) {
                                                                                                                                                                                    builder.dismiss();
                                                                                                                                                                                    startActivity(new Intent(getApplicationContext(),HomeACTIVITY.class));

                                                                                                                                                                                }
                                                                                                                                                                            }).show();
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        });
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    });

                                                                                                                        }
                                                                                                                    }
                                                                                                                }). setIcon(R.drawable.neww__logo)
                                                                                                                .setView(input)
                                                                                                                .show();
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        });

                                                                            }

                                                                        }
                                                                    }).create().show();

                                                        }
                                                        else if (i==2) {
                                                            //////
                                                            AlertDialog.Builder builder1=new AlertDialog.Builder(MapsActivity.this);
                                                            String options[]={"Hand Cash","Nogod","Bkash"};
                                                            builder1.setTitle("Payment Option")
                                                                    .setItems(options, new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                                            if (i==0) {
                                                                                dialogInterface.dismiss();
                                                                                final KProgressHUD progressDialog=  KProgressHUD.create(MapsActivity.this)
                                                                                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                                                                        .setLabel("Uploading Data.....")
                                                                                        .setCancellable(false)
                                                                                        .setAnimationSpeed(2)
                                                                                        .setDimAmount(0.5f)
                                                                                        .show();
                                                                                Long tsLong = System.currentTimeMillis()/1000;
                                                                                String  ts = tsLong.toString();
                                                                                String uuid= UUID.randomUUID().toString();
                                                                                double maindistance=distance / 1000;
                                                                                mainbus=maindistance*(Double.parseDouble(cngrate));
                                                                                //  cng.setText("CNG\n"+String.format("%.2f", mainbus) + "Tk");

                                                                                OrderModel orderModel=new OrderModel(fromdistance6,todiatance,diatance6,datee.getText().toString(),
                                                                                        "Pending",ts,uuid,phone,"CNG",String.format("%.2f", mainbus) + "Tk",firebaseAuth.getCurrentUser().getEmail(),"Pending Ride","Pending Ride","Hand Cash","hand Cash",ride);
                                                                                ///
                                                                                firebaseFirestore.collection("MyRide")
                                                                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                        .collection("list")
                                                                                        .document(uuid)
                                                                                        .set(orderModel)
                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    firebaseFirestore.collection("AdminRequest")
                                                                                                            .document(uuid)
                                                                                                            .set(orderModel)
                                                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                @Override
                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                    if (task.isSuccessful()) {
                                                                                                                        progressDialog.dismiss();

                                                                                                                        new AestheticDialog.Builder(MapsActivity.this, DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                                                                .setTitle("Conformation")
                                                                                                                                .setMessage("Pickup request send to admin..\nNow admin will conact with you.")
                                                                                                                                .setAnimation(DialogAnimation.SPLIT)
                                                                                                                                .setOnClickListener(new OnDialogClickListener() {
                                                                                                                                    @Override
                                                                                                                                    public void onClick(AestheticDialog.Builder builder) {
                                                                                                                                        builder.dismiss();
                                                                                                                                        startActivity(new Intent(getApplicationContext(),HomeACTIVITY.class));

                                                                                                                                    }
                                                                                                                                }).show();
                                                                                                                    }
                                                                                                                }
                                                                                                            });
                                                                                                }
                                                                                            }
                                                                                        });
                                                                            }
                                                                            else if (i==1) {
                                                                                firebaseFirestore.collection("Payment__number")
                                                                                        .document("nogod@gmail.com")
                                                                                        .get()
                                                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    if (task.getResult().exists()) {
                                                                                                        final EditText input = new EditText(MapsActivity.this);
                                                                                                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                                                                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                                                                                LinearLayout.LayoutParams.MATCH_PARENT);
                                                                                                        input.setLayoutParams(lp);
                                                                                                        input.setGravity(Gravity.CENTER);
                                                                                                        input.setHint("Enter Phonenumber");

                                                                                                        new androidx.appcompat.app.AlertDialog.Builder(MapsActivity.this)
                                                                                                                .setTitle("Payment Number ")
                                                                                                                .setMessage("Send Payment This Number : \nNogod Personal : "+task.getResult().getString("number"))
                                                                                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                                                                    @Override
                                                                                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                                                                                        if (TextUtils.isEmpty(input.getText().toString()))
                                                                                                                        {
                                                                                                                            Toast.makeText(MapsActivity.this, "Enter Values", Toast.LENGTH_SHORT).show();
                                                                                                                        }
                                                                                                                        else {
                                                                                                                            dialogInterface.dismiss();
                                                                                                                            final KProgressHUD progressDialog=  KProgressHUD.create(MapsActivity.this)
                                                                                                                                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                                                                                                                    .setLabel("Uploading Data.....")
                                                                                                                                    .setCancellable(false)
                                                                                                                                    .setAnimationSpeed(2)
                                                                                                                                    .setDimAmount(0.5f)
                                                                                                                                    .show();
                                                                                                                            Long tsLong = System.currentTimeMillis()/1000;
                                                                                                                            String  ts = tsLong.toString();
                                                                                                                            String uuid= UUID.randomUUID().toString();
                                                                                                                            double maindistance=distance / 1000;
                                                                                                                            mainbus=maindistance*(Double.parseDouble(cngrate));
                                                                                                                            //  cng.setText("CNG\n"+String.format("%.2f", mainbus) + "Tk");

                                                                                                                            OrderModel orderModel=new OrderModel(fromdistance6,todiatance,diatance6,datee.getText().toString(),
                                                                                                                                    "Pending",ts,uuid,phone,"CNG",String.format("%.2f", mainbus) + "Tk",firebaseAuth.getCurrentUser().getEmail(),"Pending Ride","Pending Ride","Nogod",input.getText().toString(),ride);
                                                                                                                            ///
                                                                                                                            Calendar calendar=Calendar.getInstance();
                                                                                                                            final  int year=calendar.get(Calendar.YEAR);
                                                                                                                            final int month=calendar.get(Calendar.MONTH);
                                                                                                                            final int day=calendar.get(Calendar.DAY_OF_MONTH);
                                                                                                                            firebaseFirestore.collection("Payment")
                                                                                                                                    .document(""+year)
                                                                                                                                    .collection(""+month)
                                                                                                                                    .document(""+day)
                                                                                                                                    .collection("List")
                                                                                                                                    .document(uuid)
                                                                                                                                    .set(orderModel)
                                                                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                        @Override
                                                                                                                                        public void onComplete(@NonNull Task<Void> task) {

                                                                                                                                        }
                                                                                                                                    });
                                                                                                                            firebaseFirestore.collection("MyRide")
                                                                                                                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                                    .collection("list")
                                                                                                                                    .document(uuid)
                                                                                                                                    .set(orderModel)
                                                                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                        @Override
                                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                            if (task.isSuccessful()) {
                                                                                                                                                firebaseFirestore.collection("AdminRequest")
                                                                                                                                                        .document(uuid)
                                                                                                                                                        .set(orderModel)
                                                                                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                            @Override
                                                                                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                                if (task.isSuccessful()) {
                                                                                                                                                                    progressDialog.dismiss();

                                                                                                                                                                    new AestheticDialog.Builder(MapsActivity.this, DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                                                                                                            .setTitle("Conformation")
                                                                                                                                                                            .setMessage("Pickup request send to admin..\nNow admin will conact with you.")
                                                                                                                                                                            .setAnimation(DialogAnimation.SPLIT)
                                                                                                                                                                            .setOnClickListener(new OnDialogClickListener() {
                                                                                                                                                                                @Override
                                                                                                                                                                                public void onClick(AestheticDialog.Builder builder) {
                                                                                                                                                                                    builder.dismiss();
                                                                                                                                                                                    startActivity(new Intent(getApplicationContext(),HomeACTIVITY.class));

                                                                                                                                                                                }
                                                                                                                                                                            }).show();
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        });
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    });

                                                                                                                        }
                                                                                                                    }
                                                                                                                }). setIcon(R.drawable.neww__logo)
                                                                                                                .setView(input)
                                                                                                                .show();
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        });

                                                                            }
                                                                            //
                                                                            else if (i==2) {
                                                                                firebaseFirestore.collection("Payment__number")
                                                                                        .document("bkash@gmail.com")
                                                                                        .get()
                                                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    if (task.getResult().exists()) {
                                                                                                        final EditText input = new EditText(MapsActivity.this);
                                                                                                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                                                                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                                                                                LinearLayout.LayoutParams.MATCH_PARENT);
                                                                                                        input.setLayoutParams(lp);
                                                                                                        input.setGravity(Gravity.CENTER);
                                                                                                        input.setHint("Enter Phonenumber");

                                                                                                        new androidx.appcompat.app.AlertDialog.Builder(MapsActivity.this)
                                                                                                                .setTitle("Payment Number ")
                                                                                                                .setMessage("Send Payment This Number : \nBkash Personal : "+task.getResult().getString("number"))
                                                                                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                                                                    @Override
                                                                                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                                                                                        if (TextUtils.isEmpty(input.getText().toString()))
                                                                                                                        {
                                                                                                                            Toast.makeText(MapsActivity.this, "Enter Values", Toast.LENGTH_SHORT).show();
                                                                                                                        }
                                                                                                                        else {
                                                                                                                            dialogInterface.dismiss();
                                                                                                                            final KProgressHUD progressDialog=  KProgressHUD.create(MapsActivity.this)
                                                                                                                                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                                                                                                                    .setLabel("Uploading Data.....")
                                                                                                                                    .setCancellable(false)
                                                                                                                                    .setAnimationSpeed(2)
                                                                                                                                    .setDimAmount(0.5f)
                                                                                                                                    .show();
                                                                                                                            ;
                                                                                                                            Long tsLong = System.currentTimeMillis()/1000;
                                                                                                                            String  ts = tsLong.toString();
                                                                                                                            String uuid= UUID.randomUUID().toString();
                                                                                                                            double maindistance=distance / 1000;
                                                                                                                            mainbus=maindistance*(Double.parseDouble(cngrate));
                                                                                                                            //  cng.setText("CNG\n"+String.format("%.2f", mainbus) + "Tk");

                                                                                                                            OrderModel orderModel=new OrderModel(fromdistance6,todiatance,diatance6,datee.getText().toString(),
                                                                                                                                    "Pending",ts,uuid,phone,"CNG",String.format("%.2f", mainbus) + "Tk",firebaseAuth.getCurrentUser().getEmail(),"Pending Ride","Pending Ride",
                                                                                                                                    "Bkash",input.getText().toString(),ride);
                                                                                                                            ///
                                                                                                                            Calendar calendar=Calendar.getInstance();
                                                                                                                            final  int year=calendar.get(Calendar.YEAR);
                                                                                                                            final int month=calendar.get(Calendar.MONTH);
                                                                                                                            final int day=calendar.get(Calendar.DAY_OF_MONTH);
                                                                                                                            firebaseFirestore.collection("Payment")
                                                                                                                                    .document(""+year)
                                                                                                                                    .collection(""+month)
                                                                                                                                    .document(""+day)
                                                                                                                                    .collection("List")
                                                                                                                                    .document(uuid)
                                                                                                                                    .set(orderModel)
                                                                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                        @Override
                                                                                                                                        public void onComplete(@NonNull Task<Void> task) {

                                                                                                                                        }
                                                                                                                                    });
                                                                                                                            firebaseFirestore.collection("MyRide")
                                                                                                                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                                    .collection("list")
                                                                                                                                    .document(uuid)
                                                                                                                                    .set(orderModel)
                                                                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                        @Override
                                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                            if (task.isSuccessful()) {
                                                                                                                                                firebaseFirestore.collection("AdminRequest")
                                                                                                                                                        .document(uuid)
                                                                                                                                                        .set(orderModel)
                                                                                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                            @Override
                                                                                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                                if (task.isSuccessful()) {
                                                                                                                                                                    progressDialog.dismiss();

                                                                                                                                                                    new AestheticDialog.Builder(MapsActivity.this, DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                                                                                                            .setTitle("Conformation")
                                                                                                                                                                            .setMessage("Pickup request send to admin..\nNow admin will conact with you.")
                                                                                                                                                                            .setAnimation(DialogAnimation.SPLIT)
                                                                                                                                                                            .setOnClickListener(new OnDialogClickListener() {
                                                                                                                                                                                @Override
                                                                                                                                                                                public void onClick(AestheticDialog.Builder builder) {
                                                                                                                                                                                    builder.dismiss();
                                                                                                                                                                                    startActivity(new Intent(getApplicationContext(),HomeACTIVITY.class));

                                                                                                                                                                                }
                                                                                                                                                                            }).show();
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        });
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    });

                                                                                                                        }
                                                                                                                    }
                                                                                                                }). setIcon(R.drawable.neww__logo)
                                                                                                                .setView(input)
                                                                                                                .show();
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        });

                                                                            }

                                                                        }
                                                                    }).create().show();


                                                        }
                                                    }
                                                }).create().show();
                                    }
                                }).create().show();

                            }
                        }
                    });

                    dialogClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialog.dismiss();
                        }
                    });
                    mDialog.show();
                }
            }
        });
        //make

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
                    Geocoder geocoder=new Geocoder(MapsActivity.this, Locale.getDefault());
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
                            distance_map.setText(String.format("%.2f", distance / 1000) + "km");
                            mapFragment.getMapAsync(MapsActivity.this);
                            double maindistance=distance / 1000;
                             mainbus=maindistance*(Double.parseDouble(cngrate));
                            cng.setText("CNG\n"+String.format("%.2f", mainbus) + "Tk");
                             mainbike=maindistance*(Double.parseDouble(bikerate));
                            bike.setText("BIKE\n"+String.format("%.2f", mainbike) + "Tk");
                             maincar=maindistance*(Double.parseDouble(carrate));
                            car.setText("CAR\n"+String.format("%.2f", maincar) + "Tk");
                           // dailyCheckCard.setClickable(true);
                           // Toast.makeText(MapsActivity.this, ""+"Distance between Two Place  is \n " + String.format("%.2f", distance / 1000) + "km", Toast.LENGTH_SHORT).show();
                            //texts.setText("Distance between Two Place  is \n " + String.format("%.2f", distance / 1000) + "km");
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

       // client1= LocationServices.getFusedLocationProviderClient(view.getContext());
    }

    private int mYear, mMonth, mDay, mHour, mMinute;
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        datee.setText(currentDateString);
    }
    Location currentLocation;

    private static final int REQUEST_CODE = 101;

    double mainbus,mainbike,maincar;
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    LatLng barcelona;
    LatLng madrid;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
            mMap.setTrafficEnabled(true);



        }
       /*
        // on below line we will be adding polyline on Google Maps.
        mMap.addPolyline((new PolylineOptions()).add(NewCastle,Brisbane).
                // below line is use to specify the width of poly line.
                        width(5)
                // below line is use to add color to our poly line.
                .color(Color.RED)
                // below line is to make our poly line geodesic.
                .geodesic(true));
        // on below line we will be starting the drawing of polyline.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(NewCastle, 13));
        LatLng latLng = new LatLng(24.75636, 90.40646);
        Marker melbourne = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Melbourne"));
        melbourne.showInfoWindow();
    }

    Location mLastLocation;
    Marker mCurrLocationMarker;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    LatLng TamWorth = new LatLng(-31.083332, 150.916672);
    LatLng NewCastle =new LatLng(24.75636, 90.40646);
    LatLng Brisbane = new LatLng(22.70497, 90.37013);
        */
        if (TextUtils.isEmpty(fromdistance.getText().toString())||TextUtils.isEmpty(toistance.getText().toString())) {

            return;
        }
        else {
            String locationAddress=fromdistance.getText().toString();
            String locationAddress1=toistance.getText().toString();
            Geocoder geocoder=new Geocoder(MapsActivity.this, Locale.getDefault());
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
                     barcelona = new LatLng(address.getLatitude(),address.getLongitude());
                     madrid =new LatLng(address1.getLatitude(),address1.getLongitude());

                    distance = SphericalUtil.computeDistanceBetween(barcelona, madrid);
                    // barcelona = new LatLng(22.70497, 90.37013);
                    // mMap.addMarker(new MarkerOptions().position(barcelona).title("Marker in Barcelona"));
                    Marker melbourne = mMap.addMarker(new MarkerOptions()
                            .position(barcelona)
                            .title("Start Point"));
                    melbourne.showInfoWindow();

                     //madrid = new LatLng(24.75636, 90.40646);
                    //mMap.addMarker(new MarkerOptions().position(madrid).title("Marker in Madrid"));
                    Marker madridq = mMap.addMarker(new MarkerOptions()
                            .position(madrid)
                            .title("End Point"));
                    madridq.showInfoWindow();
                    LatLng zaragoza = new LatLng(41.648823,-0.889085);

                    //Define list to get all latlng for the route
                    List<LatLng> path = new ArrayList();

                    mMap.addPolyline((new PolylineOptions()).add(barcelona,madrid).
                            // below line is use to specify the width of poly line.
                                    width(5)
                            // below line is use to add color to our poly line.
                            .color(Color.RED)
                            // below line is to make our poly line geodesic.
                            .geodesic(true));
                    //Execute Directions API request
                    GeoApiContext context = new GeoApiContext.Builder()
                            .apiKey("AIzaSyBrPt88vvoPDDn_imh-RzCXl5Ha2F2LYig")
                            .build();
                    DirectionsApiRequest req = DirectionsApi.getDirections(context, String.valueOf(barcelona), String.valueOf(madrid));
                    try {
                        DirectionsResult res = req.await();

                        //Loop through legs and steps to get encoded polylines of each step
                        if (res.routes != null && res.routes.length > 0) {
                            DirectionsRoute route = res.routes[0];

                            if (route.legs !=null) {
                                for(int i=0; i<route.legs.length; i++) {
                                    DirectionsLeg leg = route.legs[i];
                                    if (leg.steps != null) {
                                        for (int j=0; j<leg.steps.length;j++){
                                            DirectionsStep step = leg.steps[j];
                                            if (step.steps != null && step.steps.length >0) {
                                                for (int k=0; k<step.steps.length;k++){
                                                    DirectionsStep step1 = step.steps[k];
                                                    EncodedPolyline points1 = step1.polyline;
                                                    if (points1 != null) {
                                                        //Decode polyline and add points to list of route coordinates
                                                        List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
                                                        for (com.google.maps.model.LatLng coord1 : coords1) {
                                                            path.add(new LatLng(coord1.lat, coord1.lng));
                                                        }
                                                    }
                                                }
                                            } else {
                                                EncodedPolyline points = step.polyline;
                                                if (points != null) {
                                                    //Decode polyline and add points to list of route coordinates
                                                    List<com.google.maps.model.LatLng> coords = points.decodePath();
                                                    for (com.google.maps.model.LatLng coord : coords) {
                                                        path.add(new LatLng(coord.lat, coord.lng));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch(Exception ex) {
                        //  Log.e(TAG, ex.getLocalizedMessage());
                    }

                    //Draw the polyline
                    if (path.size() > 0) {
                        PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.RED).width(5);
                        mMap.addPolyline(opts);
                    }

                    mMap.getUiSettings().setZoomControlsEnabled(true);

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(madrid, 6));
                //    distance_map.setText(String.format("%.2f", distance / 1000) + "km");
                   // mapFragment.getMapAsync(MapsActivity.this);
                    // Toast.makeText(MapsActivity.this, ""+"Distance between Two Place  is \n " + String.format("%.2f", distance / 1000) + "km", Toast.LENGTH_SHORT).show();
                    //texts.setText("Distance between Two Place  is \n " + String.format("%.2f", distance / 1000) + "km");
                    // Toast.makeText(getApplicationContext(), "Distance between Two Place  is \n " + String.format("%.2f", distance / 1000) + "km", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toasty.info(getApplicationContext(),"Donot Find A Location.",Toasty.LENGTH_SHORT,true).show();
                    return;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    private String TAG = "so47492459";



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.search,menu);
        return true;

    }

    public void searchagain(View view) {

    }
    Location mLastLocation;
    Marker mCurrLocationMarker;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }


}