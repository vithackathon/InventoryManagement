package com.vit.trf.inventorymanagement;

import android.animation.Animator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Home extends AppCompatActivity implements View.OnClickListener{
    CardView borrowCard,returnCard,addCard,settingCard,wifiCard ;
    Intent i ;
    LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        ll = (LinearLayout) findViewById(R.id.ll);
        borrowCard = (CardView) findViewById(R.id.borrow);
        returnCard = (CardView) findViewById(R.id.returnID);
        addCard = (CardView) findViewById(R.id.add);
        settingCard = (CardView) findViewById(R.id.links);
        wifiCard = (CardView) findViewById(R.id.wifi);

        borrowCard.setOnClickListener(this);
        returnCard.setOnClickListener(this);
        addCard.setOnClickListener(this);
        settingCard.setOnClickListener(this);
        wifiCard.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            case R.id.borrow :
                Toast.makeText(this, "bank", Toast.LENGTH_SHORT).show();
                break;

            case R.id.returnID:
                Toast.makeText(this, "ideas", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}