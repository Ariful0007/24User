package com.meass.a24user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class HomeACTIVITY extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nav_view;
    CardView card_view2;
    KProgressHUD progressHUD;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String  email;
    int count = 0,count1=0;
    TextView totaluser,paid,pending,blocking,notificationn,invalid,todaystask,margo_signup;
    int count11=0,countpaid,block=0;


    //
    EditText methodename,minwith,hint;
    Button logo,login_button;
    ImageView myImage;
    FirebaseStorage storage;
    StorageReference storageReference;
    Dialog mDialog;
    EditText name,ammount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_a_c_t_i_v_i_t_y);
        Toolbar toolbar = findViewById(R.id.toolbar);

        blocking=findViewById(R.id.blocking);
        HomeFragment fragment2 = new HomeFragment();


        FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
        ft2.replace(R.id.content, fragment2, "");
        ft2.commit();



        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        drawerLayout = findViewById(R.id.drawer);
        nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(this);
        progressHUD = KProgressHUD.create(HomeACTIVITY.this);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.sos:
                firebaseFirestore.collection("Rates")
                        .document("rates@gmail.com")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {
                                        Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
                                        intent.putExtra("car",task.getResult().getString("car"));
                                        intent.putExtra("bus",task.getResult().getString("bus"));
                                        intent.putExtra("bike",task.getResult().getString("bike"));
                                        intent.putExtra("ride","Emergency Ride");
                                        startActivity(intent);
                                    }
                                }
                            }
                        });

                break;
            case R.id.bookings:
                startActivity(new Intent(getApplicationContext(),BookingList.class));
                break;
            case R.id.adminmessage:
                final FlatDialog flatDialog = new FlatDialog(HomeACTIVITY.this);
                flatDialog.setCancelable(false);
                flatDialog.setTitle("Send a message")

                        .setTitleColor(Color.parseColor("#000000"))
                        .setBackgroundColor(Color.parseColor("#f5f0e3"))
                        .setLargeTextFieldHint("write your text here ...")
                        .setLargeTextFieldHintColor(Color.parseColor("#000000"))
                        .setLargeTextFieldBorderColor(Color.parseColor("#000000"))
                        .setLargeTextFieldTextColor(Color.parseColor("#000000"))
                        .setFirstButtonColor(Color.parseColor("#fda77f"))
                        .setFirstButtonTextColor(Color.parseColor("#000000"))
                        .setFirstButtonText("Done")

                        .  setSecondButtonText("Cancel")
                        .withFirstButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                flatDialog.dismiss();
                                String uuid= UUID.randomUUID().toString();
                                Map<String, String> userMap41 = new HashMap<>();
                                userMap41.put("username",firebaseAuth.getCurrentUser().getEmail());
                                userMap41.put("uuid",uuid);

                                userMap41.put("feedback", flatDialog.getLargeTextField().toLowerCase());
                                firebaseFirestore=FirebaseFirestore.getInstance();
                                firebaseFirestore.collection("Customer_feedback")
                                        .document(uuid)
                                        .set(userMap41)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toasty.success(getApplicationContext(),"Send",Toasty.LENGTH_SHORT,true).show();
                                                }
                                            }
                                        });


                            }
                        })
                        .withSecondButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                flatDialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.helpl:
                firebaseFirestore.collection("HelpLineNumber")
                        .document("abc@gmail.com")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {
                                        try {
                                            String number=task.getResult().getString("number");
                                            String s="tel:"+number;
                                            Intent intent33=new Intent(Intent.ACTION_DIAL);
                                            intent33.setData(Uri.parse(s));
                                            startActivity(intent33);
                                        }catch (Exception e) {
                                            String number=task.getResult().getString("number");
                                            String s="tel:"+number;
                                            Intent intent33=new Intent(Intent.ACTION_DIAL);
                                            intent33.setData(Uri.parse(s));
                                            startActivity(intent33);
                                        }


                                    }
                                    else {
                                        Toasty.info(getApplicationContext(),"No helpline number found.",Toasty.LENGTH_SHORT,true).show();
                                    }
                                }
                            }
                        });

                break;

            case R.id.addNote:
                HomeFragment homeFragment = new HomeFragment();


                FragmentTransaction ft2w = getSupportFragmentManager().beginTransaction();
                ft2w.replace(R.id.content, homeFragment, "");
                ft2w.commit();

                Toasty.success(getApplicationContext(),"You Are Home Now",Toasty.LENGTH_SHORT,true).show();
                break;
            case R.id.bell:
                startActivity(new Intent(getApplicationContext(),NoticeBoard.class));
                break;
            case R.id.promotion:
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                break;
            case R.id.aboutcompany:
                startActivity(new Intent(getApplicationContext(),AboutCompany.class));
                break;
            case R.id.shareapp2:
                AlertDialog.Builder warning = new AlertDialog.Builder(HomeACTIVITY.this)
                        .setTitle("Logout")
                        .setMessage("Are you want to logout?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();



                            }
                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // ToDO: delete all the notes created by the Anon user


                                firebaseAuth.signOut();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();


                            }
                        });

                warning.show();


                break;
        }


        return false;
    }

    public void logout(View view) {
        Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {


        AlertDialog.Builder builder = new AlertDialog.Builder(HomeACTIVITY.this);
        builder.setTitle("Exit")
                .setMessage("Are you want to exit?")
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();


                    }
                }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finishAffinity();
            }
        });
        builder.create().show();
    }


}