package com.example.chatzup.Models;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.chatzup.Adapters.FragmentsAdapters;
import com.example.chatzup.GroupChatActivity;
import com.example.chatzup.MainActivity;
import com.example.chatzup.R;
import com.example.chatzup.SettingsActivity;
import com.example.chatzup.databinding.ActivityDashBoradBinding;
import com.example.chatzup.sign_in_activity;
import com.google.firebase.auth.FirebaseAuth;

public class DashBorad extends AppCompatActivity {

    ActivityDashBoradBinding binding;
    FirebaseAuth auth;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashBoradBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(DashBorad.this,R.color.black));

        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#000000"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);




        auth = FirebaseAuth.getInstance();
        binding.viewpager.setAdapter(new FragmentsAdapters(getSupportFragmentManager()));
binding.tablayout.setupWithViewPager(binding.viewpager);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.settings:
              Intent i = new Intent (DashBorad.this, SettingsActivity.class);
              startActivity(i);

                break;
            case R.id.logout:
                auth.signOut();
                Intent intent = new Intent(DashBorad.this, sign_in_activity.class);
                startActivity(intent);

                break;

            case R.id.groupchat:
                    Intent intentt = new Intent(DashBorad.this, GroupChatActivity.class);
                    startActivity(intentt);

                break;
        }
        return false;
    }

}