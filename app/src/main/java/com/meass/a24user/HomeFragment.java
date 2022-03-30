package com.meass.a24user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.airbnb.lottie.LottieAnimationView;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import es.dmoral.toasty.Toasty;


public class HomeFragment extends Fragment {

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    LottieAnimationView empty_cart;
    DocumentReference documentReference;
    RecyclerView recyclerView;

    FirebaseUser firebaseUser;
    KProgressHUD progressHUD;
    String cus_name;
    private StaggeredGridLayoutManager mLayoutManager;
    View view;
    private UserSession session;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view= inflater.inflate(R.layout.fragment_home, container, false);
        session =new UserSession(view.getContext());
firebaseAuth=FirebaseAuth.getInstance();
firebaseFirestore=FirebaseFirestore.getInstance();
     //   tapview();
        if (session.getFirstTime()) {
            //tap target view
            new AestheticDialog.Builder((Activity) view.getContext(), DialogStyle.FLASH, DialogType.SUCCESS)
                    .setTitle("Congratulation")
                    .setMessage("Welcome to BD RIDE Apps.\nYou Can find better experience here.")
                    .setAnimation(DialogAnimation.SPLIT)
                    .setOnClickListener(new OnDialogClickListener() {
                        @Override
                        public void onClick(AestheticDialog.Builder builder) {
                            builder.dismiss();
                            tapview();
                            session.setFirstTime(false);

                        }
                    }).show();

        }
        CardView luckySpinCard=view.findViewById(R.id.luckySpinCard);
        luckySpinCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection("Rates")
                        .document("rates@gmail.com")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {
                                        Intent intent=new Intent(getContext(),MapsActivity.class);
                                        intent.putExtra("ride","Menual Ride");
                                        intent.putExtra("car",task.getResult().getString("car"));
                                        intent.putExtra("bus",task.getResult().getString("bus"));
                                        intent.putExtra("bike",task.getResult().getString("bike"));
                                        startActivity(intent);
                                    }
                                }
                            }
                        });
                //startActivity(new Intent(view.getContext(),MapsActivity.class));
            }
        });
        CardView dailyCheckCard=view.findViewById(R.id.dailyCheckCard);
        dailyCheckCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.success(view.getContext(),"You are home now.",Toasty.LENGTH_SHORT,true).show();
            }
        });
        CardView watchCard=view.findViewById(R.id.watchCard);
        watchCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TapTargetSequence((Activity) getContext())
                        .targets(
                                TapTarget.forView(view.findViewById(R.id.watchCard), "Food Delivery", "This feature will come in shortly.")
                                        .targetCircleColor(R.color.colorAccent)
                                        .titleTextColor(R.color.white)
                                        .titleTextSize(25)
                                        .descriptionTextSize(15)
                                        .descriptionTextColor(R.color.white)
                                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                                        .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                                        .tintTarget(true)
                                        .transparentTarget(true)
                                        .outerCircleColor(R.color.first))

                        .listener(new TapTargetSequence.Listener() {
                            // This listener will tell us when interesting(tm) events happen in regards
                            // to the sequence
                            @Override
                            public void onSequenceFinish() {
                                //session.setFirstTime(false);
                                Toasty.success(getContext(), " You are ready to go !", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                            }

                            @Override
                            public void onSequenceCanceled(TapTarget lastTarget) {
                                // Boo
                            }
                        }).start();
            }
        });
        CardView taskCard=view.findViewById(R.id.taskCard);
        taskCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection("Rates")
                        .document("rates@gmail.com")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {
                                        Intent intent=new Intent(getContext(),MapsActivity.class);
                                        intent.putExtra("ride","Menual Ride");
                                        intent.putExtra("car",task.getResult().getString("car"));
                                        intent.putExtra("bus",task.getResult().getString("bus"));
                                        intent.putExtra("bike",task.getResult().getString("bike"));
                                        startActivity(intent);
                                    }
                                }
                            }
                        });
            }
        });
        CardView referCard=view.findViewById(R.id.referCard);
        referCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection("Rates")
                        .document("rates@gmail.com")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {
                                        Intent intent=new Intent(getContext(),MapsActivity.class);
                                        intent.putExtra("ride","Menual Ride");
                                        intent.putExtra("car",task.getResult().getString("car"));
                                        intent.putExtra("bus",task.getResult().getString("bus"));
                                        intent.putExtra("bike",task.getResult().getString("bike"));
                                        startActivity(intent);
                                    }
                                }
                            }
                        });
            }
        });
        return  view;
    }
    private void tapview() {

        new TapTargetSequence((Activity) getContext())
                .targets(
                        TapTarget.forView(view.findViewById(R.id.luckySpinCard), "Car Ride", "Get a better experience here.")
                                .targetCircleColor(R.color.colorAccent)
                                .titleTextColor(R.color.white)
                                .titleTextSize(25)
                                .descriptionTextSize(15)
                                .descriptionTextColor(R.color.white)
                                .drawShadow(true)                   // Whether to draw a drop shadow or not
                                .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                                .tintTarget(true)
                                .transparentTarget(true)
                                .outerCircleColor(R.color.first),
                        TapTarget.forView(view.findViewById(R.id.taskCard), "CNG Ride", "Get a better experience here.")
                                .targetCircleColor(R.color.colorAccent)
                                .titleTextColor(R.color.white)
                                .titleTextSize(25)
                                .descriptionTextSize(15)
                                .descriptionTextColor(R.color.white)
                                .drawShadow(true)                   // Whether to draw a drop shadow or not
                                .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                                .tintTarget(true)
                                .transparentTarget(true)
                                .outerCircleColor(R.color.third),
                        TapTarget.forView(view.findViewById(R.id.referCard), "Bike Ride", "Get a better experience here.")
                                .targetCircleColor(R.color.colorAccent)
                                .titleTextColor(R.color.white)
                                .titleTextSize(25)
                                .descriptionTextSize(15)
                                .descriptionTextColor(R.color.white)
                                .drawShadow(true)
                                .cancelable(false)// Whether tapping outside the outer circle dismisses the view
                                .tintTarget(true)
                                .transparentTarget(true)
                                .outerCircleColor(R.color.second),
                        TapTarget.forView(view.findViewById(R.id.watchCard), "Food Delivery", "Get a better experience here.")
                                .targetCircleColor(R.color.colorAccent)
                                .titleTextColor(R.color.white)
                                .titleTextSize(25)
                                .descriptionTextSize(15)
                                .descriptionTextColor(R.color.white)
                                .drawShadow(true)
                                .cancelable(false)// Whether tapping outside the outer circle dismisses the view
                                .tintTarget(true)
                                .transparentTarget(true)
                                .outerCircleColor(R.color.fourth))
                .listener(new TapTargetSequence.Listener() {
                    // This listener will tell us when interesting(tm) events happen in regards
                    // to the sequence
                    @Override
                    public void onSequenceFinish() {
                        //session.setFirstTime(false);
                        Toasty.success(getContext(), " You are ready to go !", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        // Boo
                    }
                }).start();

    }
}