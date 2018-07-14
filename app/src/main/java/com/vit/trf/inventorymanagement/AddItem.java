package com.vit.trf.inventorymanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AddItem extends AppCompatActivity {

    EditText et1,et2,et3;
    Button b1,b2;

    //private FirebaseDatabase database ;
    //private DatabaseReference databaseReference;
   // FirebaseDatabase database = FirebaseDatabase.getInstance();
   // DatabaseReference myRef = database.getReference("allitems");
    DatabaseReference m_objFireBaseRef  = FirebaseDatabase.getInstance().getReference();
    DatabaseReference objRef = m_objFireBaseRef.child("allitems");
   // DatabaseReference borrowedRef = m_objFireBaseRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()+"/Borrowed");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);


        et1 = (EditText)findViewById(R.id.itemname);
        et2 = (EditText)findViewById(R.id.itemquantity);
        et3 = (EditText)findViewById(R.id.removename);
        b1 = (Button)findViewById(R.id.additem);
        b2 = (Button)findViewById(R.id.removeitem);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        try{
               final String name;
                final int q;
                name=et1.getText().toString();
                q=Integer.parseInt(et2.getText().toString());

          /*  Map<String, String> userData = new HashMap<String, String>();

            userData.put(name, q);
            myRef.push().setValue(userData);
                //myRef.child(name).push().setValue(q);
            //  DatabaseReference childDBR = myRef.child(name);
            //childDBR.child("quantity").setValue(q);*/



            objRef.child(name).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    int quantity=0;
                    try {
                        if (snapshot.getValue() != null) {
                            try {
                                 quantity = q + Integer.parseInt(snapshot.getValue().toString());// your name values you will get here

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            //Log.e("TAG", " it's null.");
                                quantity = q;

                        }

                        objRef.child(name).setValue(quantity);
                        Toast.makeText(AddItem.this, "Item Added", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });
        }
        catch (Exception e){
            Toast.makeText(AddItem.this, "Exception "+e, Toast.LENGTH_SHORT).show();

        }

            }
        });

      /*  b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try{
                    String name;
                    name=et3.getText().toString();

                    DatabaseReference childDBR = myRef.child(name);

                    childDBR.removeValue();
                }catch (Exception e){}


            }
        });*/
    }
}
