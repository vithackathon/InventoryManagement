package com.vit.trf.inventorymanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;


public class ResetPasswordActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private FloatingActionButton floatingActionButtonForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        editTextEmail=(EditText)findViewById(R.id.forgotEmail1);
        floatingActionButtonForgotPassword=(FloatingActionButton)findViewById(R.id.forgotSubmit1);
        floatingActionButtonForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editTextEmail.getText().toString().isEmpty() && editTextEmail.getText().toString().contains("@") && editTextEmail.getText().toString().length()>4)
                {
                    sendLink(editTextEmail.getText().toString());
                    Intent intentToMainActivity=new Intent(ResetPasswordActivity.this,MainActivity.class);
                    startActivity(intentToMainActivity);
                }
                else
                {
                    editTextEmail.setError("Enter valid email-id");
                }
            }
        });
    }

    void sendLink(String email)
    {
        FirebaseAuth auth = FirebaseAuth.getInstance();
       // final SpotsDialog spotsDialog = new SpotsDialog(ResetPasswordActivity.this, R.style.Custom1);
        //spotsDialog.show();

        try {


            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetPasswordActivity.this, "Reset link sent", Toast.LENGTH_LONG).show();
                                Intent intentToMain=new Intent(ResetPasswordActivity.this,MainActivity.class);
                                startActivity(intentToMain);
                                //spotsDialog.dismiss();
                            }
                            else
                            {
                                Toast.makeText(ResetPasswordActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                              //  spotsDialog.dismiss();
                            }
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(ResetPasswordActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            //spotsDialog.dismiss();
        }
    }
}
