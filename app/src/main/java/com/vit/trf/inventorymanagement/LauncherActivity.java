package com.vit.trf.inventorymanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LauncherActivity extends AppCompatActivity {
    Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        b1 = (Button)findViewById(R.id.user);
        b2 = (Button)findViewById(R.id.admin);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LauncherActivity.this, "clicked user", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LauncherActivity.this,MainActivity.class));
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LauncherActivity.this, "clicked admin", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LauncherActivity.this,AdminVerify.class));
            }
        });
    }
}
