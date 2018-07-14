package com.vit.trf.inventorymanagement;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ybs.passwordstrengthmeter.PasswordStrength;

import dmax.dialog.SpotsDialog;

public class SignupActivity extends AppCompatActivity implements TextWatcher{



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    private EditText editText_Email,editText_Gr,editText_Password,editText_Confirm_Password,editText_Name;
    private Button button_Sign_Up;
    private FirebaseAuth mAuth;
    private static final String TAG = "UserRegistration";
    private  LinearLayout linearLayout;
    private  android.app.AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();



        linearLayout=(LinearLayout)findViewById(R.id.linearlayout_sign_up);
        editText_Name=(EditText)findViewById(R.id.input_name);
        mAuth = FirebaseAuth.getInstance();
        editText_Email=(EditText)findViewById(R.id.input_email);
        editText_Gr=(EditText)findViewById(R.id.input_grno);
        editText_Confirm_Password=(EditText)findViewById(R.id.input_confirm_password);
        button_Sign_Up=(Button)findViewById(R.id.btn_signup);

        editText_Password = (EditText)findViewById(R.id.login_password);
        editText_Password.addTextChangedListener(this);



        alertDialog= new SpotsDialog.Builder().setContext(SignupActivity.this).build();
        button_Sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(val()==1)
                {


                    alertDialog.show();
                    signUp(editText_Email.getText().toString(),editText_Password.getText().toString());

                }



            }
        });






    }


    @Override
    public void afterTextChanged(Editable s) {
    }
    @Override
    public void beforeTextChanged(
            CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        updatePasswordStrengthView(s.toString());
    }

    private void updatePasswordStrengthView(String password) {

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        TextView strengthView = (TextView) findViewById(R.id.password_strength);
        if (TextView.VISIBLE != strengthView.getVisibility())
            return;

        if (password.isEmpty()) {
            strengthView.setText("");
            progressBar.setProgress(0);
            return;
        }

        PasswordStrength str = PasswordStrength.calculateStrength(password);
        strengthView.setText(str.getText(this));
        strengthView.setTextColor(str.getColor());

        progressBar.getProgressDrawable().setColorFilter(str.getColor(), android.graphics.PorterDuff.Mode.SRC_IN);
        if (str.getText(this).equals("Weak")) {
            progressBar.setProgress(25);
        } else if (str.getText(this).equals("Medium")) {
            progressBar.setProgress(50);
        } else if (str.getText(this).equals("Strong")) {
            progressBar.setProgress(75);
        } else {
            progressBar.setProgress(100);
        }
    }

    int val()
    {
        int f;
        if(editText_Name.getText().toString().isEmpty())
        {
            editText_Name.setError("Name empty");
            return 0;
        }
        if(editText_Gr.getText().toString().isEmpty())
        {
            editText_Gr.setError("Gr no empty");
            return 0;
        }

        if(editText_Gr.getText().toString().length() != 6)
        {
            editText_Gr.setError("Invalid Gr");
            return 0;
        }
        if(editText_Email.getText().toString().isEmpty())
        {
            editText_Email.setError("Empty email");
            return 0;

        }
        if(!editText_Email.getText().toString().contains("@"))
        {
            editText_Email.setError("Invalid email");
            return 0;
        }
        if(editText_Password.getText().toString().isEmpty())
        {
            editText_Password.setError("Password empty");
            return 0;
        }
        if(editText_Confirm_Password.getText().toString().isEmpty())
        {
            Snackbar.make(linearLayout,"Please enter Confirm Password",Snackbar.LENGTH_SHORT).show();

            return 0;
        }

        PasswordStrength str = PasswordStrength.calculateStrength(editText_Password.getText().toString());
        if(str.getText(SignupActivity.this).equals("Weak") || str.getText(SignupActivity.this).equals("Medium") )
        {
            Toast.makeText(this, ""+str, Toast.LENGTH_SHORT).show();
            Snackbar.make(linearLayout,"Password must contain combination of special characters and numbers",Snackbar.LENGTH_SHORT).show();
            return 0;
        }

        if(!editText_Password.getText().toString().equals(editText_Confirm_Password.getText().toString()))
        {
            Snackbar.make(linearLayout,"Password doesn't match",Snackbar.LENGTH_SHORT).show();
            return 0;
        }
       return 1;
    }

    void signUp(final String email, String password)
    {
        try {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                               // FirebaseUser user = mAuth.getCurrentUser();
                                FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();
                                DatabaseReference databaseReference=firebaseDatabase.getReference();
                                DatabaseReference databaseReference1=databaseReference.child(user.getUid());
                                databaseReference1.child("Email").setValue(email);
                                databaseReference1.child("Name").setValue(editText_Name.getText().toString());
                                databaseReference1.child("Gr-no").setValue(editText_Gr.getText().toString());
                                DatabaseReference databaseReference2=databaseReference1.child("Borrowed");
                                databaseReference2.child("Item").setValue("None");
                                user.sendEmailVerification();
                                alertDialog.dismiss();

                                Toast.makeText(SignupActivity.this, "", Toast.LENGTH_SHORT).show();

                                Toast.makeText(SignupActivity.this, "User Registration done and Verification link sent ", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SignupActivity.this,MainActivity.class));
                                // updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                alertDialog.dismiss();
                                Toast.makeText(SignupActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                                //updateUI(null);
                            }

                            // ...
                        }
                    });
        }
        catch (Exception e)
        {
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }
    }
}

