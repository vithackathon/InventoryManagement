package com.vit.trf.inventorymanagement;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.List;
public class RecyclerViewHolders extends RecyclerView.ViewHolder{
    private static final String TAG = RecyclerViewHolders.class.getSimpleName();
    public ImageView markIcon;
    public Button itemButton;
    public ImageView deleteIcon;
    private List<Task> taskObject;





    public RecyclerViewHolders(final View itemView, final List<Task> taskObject) {
        super(itemView);
        this.taskObject = taskObject;
        itemButton = (Button)itemView.findViewById(R.id.task_title);

    }
}