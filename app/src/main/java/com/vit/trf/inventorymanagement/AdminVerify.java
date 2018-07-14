package com.vit.trf.inventorymanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminVerify extends AppCompatActivity {

    String admid;
    String admpass;

    EditText et1,et2;
    Button b1,b2;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbref = ref.child("adminid");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_verify);

        et1 = (EditText)findViewById(R.id.id);
        et2 = (EditText)findViewById(R.id.passwd);
        b1 = (Button)findViewById(R.id.confirm);







        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String uname = dataSnapshot.child("username").getValue().toString();
                        String pass = dataSnapshot.child("password").getValue().toString();

                        String euname = et1.getText().toString();
                        String epass = et2.getText().toString();//current entered details

                        if(uname.equals(euname) && pass.equals(epass))
                        {

                            Toast.makeText(AdminVerify.this, "success", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AdminVerify.this,AddItem.class));
                        }
                        else
                        {
                            Toast.makeText(AdminVerify.this, "Not successful", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        Toast.makeText(AdminVerify.this, "wrong credentials", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });


    }
}
