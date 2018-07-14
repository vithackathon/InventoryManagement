package com.vit.trf.inventorymanagement;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {
    private List<Task> task;
    DatabaseReference m_objFireBaseRef  = FirebaseDatabase.getInstance().getReference();
    DatabaseReference objRef = m_objFireBaseRef.child("allitems");
    DatabaseReference borrowedRef = m_objFireBaseRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()+"/Borrowed");
    int quantityBorrowed=0;


    protected Context context;
    public RecyclerViewAdapter(Context context, List<Task> task) {
        this.task = task;
        this.context = context;
    }
    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolders viewHolder = null;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_list, parent, false);
        viewHolder = new RecyclerViewHolders(layoutView, task);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final RecyclerViewHolders holder, int position) {
        holder.itemButton.setText(task.get(position).getTask());

        holder.itemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
               // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                //} else {
                    //builder = new AlertDialog.Builder(context);
               // }
                builder.setTitle("Borrow Item")
                        .setMessage("Are you sure you want to borrow this item?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with borrow
                               final String substr=holder.itemButton.getText().toString().substring(0,holder.itemButton.getText().toString().indexOf(":")).trim();
                                int quantity = Integer.parseInt(holder.itemButton.getText().toString().substring(holder.itemButton.getText().toString().indexOf(":")+2,holder.itemButton.getText().toString().length()));

                                objRef.child(substr).setValue(quantity-1);

                                borrowedRef.child(substr).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {

                                        try {
                                            if (snapshot.getValue() != null) {
                                                try {
                                                   quantityBorrowed = Integer.parseInt(snapshot.getValue().toString());// your name values you will get here
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                //Log.e("TAG", " it's null.");
                                                quantityBorrowed=0;

                                            }
                                            borrowedRef.child(substr).setValue(quantityBorrowed+1);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }

                                });


                                }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return this.task.size();
    }
}