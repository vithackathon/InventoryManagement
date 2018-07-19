package com.vit.trf.inventorymanagement;

import android.animation.Animator;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Home extends AppCompatActivity implements View.OnClickListener{
    CardView borrowCard,returnCard,signOutCard ;
    Intent i ;
    LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        ll = (LinearLayout) findViewById(R.id.ll);
        borrowCard = (CardView) findViewById(R.id.borrow);
        returnCard = (CardView) findViewById(R.id.returnID);
        signOutCard = (CardView) findViewById(R.id.signOut);

        borrowCard.setOnClickListener(this);
        returnCard.setOnClickListener(this);
        signOutCard.setOnClickListener(this);

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.borrow :
                startActivity(new Intent(this,BorrowActivity.class));
                break;

            case R.id.returnID:
                startActivity(new Intent(this,ReturnActivity.class));
                break;

            case R.id.signOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this,MainActivity.class));
                finish();
                break;

        }



    }
}