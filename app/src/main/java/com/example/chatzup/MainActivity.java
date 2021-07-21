package com.example.chatzup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chatzup.Models.Users;
import com.example.chatzup.databinding.ActivityMainBinding;
import com.example.chatzup.databinding.ActivitySignInActivityBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.black));
        getSupportActionBar().hide();



        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We are creating your account");

        binding.btsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.etusername.getText().toString().isEmpty())
                {
                    binding.etusername.setError("Enter Your Username");
                    return;
                }

                if (binding.etemail.getText().toString().isEmpty())
                {
                    binding.etemail.setError("Enter Your E-Mail");
                    return;
                }
                if (binding.etpassword.getText().toString().isEmpty())
                {
                    binding.etpassword.setError("Enter Your Password");
                    return;
                }
                progressDialog.show();

                auth.createUserWithEmailAndPassword(binding.etemail.getText().toString() , binding.etpassword.getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                        if (task.isSuccessful()){


                            Users user = new Users(binding.etusername.getText().toString() , binding.etemail.getText().toString() ,
                                    binding.etpassword.getText().toString() );

                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(user);

                            Toast.makeText(MainActivity.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        binding.clickforsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,sign_in_activity.class);
                startActivity(intent);
            }
        });

    }
}