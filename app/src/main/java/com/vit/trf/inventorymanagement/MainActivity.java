package com.vit.trf.inventorymanagement;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {
    private TextView textView_link_signup,textView_forgot_password;
    private  EditText editText_Email,editText_Password;
    private Button button_Signin;
    private static final String TAG = "UserLogin";
    private LinearLayout linearLayout;
    private  android.app.AlertDialog alertDialog;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView_link_signup=(TextView)findViewById(R.id.link_signup);
        button_Signin=(Button)findViewById(R.id.btn_login);
        alertDialog= new SpotsDialog.Builder().setContext(MainActivity.this).build();
        textView_forgot_password=(TextView)findViewById(R.id.forgotPassword);
        editText_Email=(EditText)findViewById(R.id.input_email1);
        editText_Password=(EditText)findViewById(R.id.input_password1);
        mAuth = FirebaseAuth.getInstance();
        linearLayout=(LinearLayout)findViewById(R.id.mainactivity);
        button_Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(val()==1)
                {
                    alertDialog.show();
                    signin(editText_Email.getText().toString(),editText_Password.getText().toString());
                }
            }
        });
        textView_link_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SignupActivity.class));
            }
        });

        textView_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ResetPasswordActivity.class));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    int val()
    {
        if(editText_Password.getText().toString().isEmpty())
        {
            Snackbar.make(linearLayout,"Password empty",Snackbar.LENGTH_SHORT);
            return 0;
        }
        if(editText_Email.getText().toString().isEmpty())
        {
            editText_Email.setError("Email cannot be empty");
            return 0;
        }
        if(!editText_Email.getText().toString().contains("@"))
        {
            editText_Email.setError("Email invalid");
            return 0;
        }
        return 1;
    }
    void signin(String email,String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user.isEmailVerified()) {
                                alertDialog.dismiss();
                                Toast.makeText(MainActivity.this, "Hello user signed in", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(MainActivity.this,Home.class);
                                startActivity(i);
                            }
                            else
                            {
                                alertDialog.dismiss();
                                Toast.makeText(MainActivity.this, "User yet to be verified", Toast.LENGTH_SHORT).show();
                            }
                          //  updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            alertDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Invalid credentials",
                                    Toast.LENGTH_SHORT).show();
                           // updateUI(null);
                        }

                        // ...
                    }
                });
    }
}
