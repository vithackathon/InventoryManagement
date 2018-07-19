package com.vit.trf.inventorymanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReturnActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerViewAdapterReturn recyclerViewAdapter;
    private DatabaseReference databaseReference;
    private List<Task> allTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);

        databaseReference = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()+"/Borrowed");


        recyclerView = (RecyclerView)findViewById(R.id.task_listR);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getAllTask(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getAllTask(dataSnapshot);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // taskDeletion(dataSnapshot);
                getAllTask(dataSnapshot);
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getAllTask(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void getAllTask(DataSnapshot dataSnapshot){
        allTask = new ArrayList<>();
        allTask.clear();
        int quantity=0;
        int flag=0;
        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
            flag =1;
            String taskTitle = singleSnapshot.getKey();
           try{  quantity = Integer.parseInt(singleSnapshot.getValue().toString());}catch (Exception e){
               singleSnapshot.getRef().removeValue();
           }
            if(quantity==0){ singleSnapshot.getRef().removeValue(); continue; }
            String item = taskTitle+" : "+quantity;
            allTask.add(new com.vit.trf.inventorymanagement.Task(item));
            recyclerViewAdapter = new RecyclerViewAdapterReturn(ReturnActivity.this, allTask);
            recyclerView.setAdapter(recyclerViewAdapter);

        }
        if(flag==0){
            recyclerViewAdapter = new RecyclerViewAdapterReturn(ReturnActivity.this, allTask);
            recyclerView.setAdapter(recyclerViewAdapter);
        }

    }

}
