package com.example.chatzup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chatzup.Models.DashBorad;
import com.example.chatzup.Models.Users;
import com.example.chatzup.databinding.ActivitySettingsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {

    ActivitySettingsBinding binding;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(SettingsActivity.this,R.color.black));
        getSupportActionBar().hide();


        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();




binding.backarrow.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent intent = new Intent(SettingsActivity.this, DashBorad.class);
        startActivity(intent);
    }
});

binding.savebutton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String status = binding.etStatus.getText().toString();
        String username = binding.etUserName.getText().toString();




    HashMap<String, Object> obj = new HashMap<>();
    obj.put("username", username);
    obj.put("status", status);

    database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
            .updateChildren(obj);
    Toast.makeText(SettingsActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();


    }
});


database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
        .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Users users = snapshot.getValue(Users.class);
                Picasso.get()
                        .load(users.getProfilepic())
                        .placeholder(R.drawable.user)
                        .into(binding.profileImage);




                binding.etUserName.setText(users.getUsername());
                binding.etStatus.setText(users.getStatus());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

binding.plus.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,33);
    }
});

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() != null){
            Uri sFile= data.getData();
            binding.profileImage.setImageURI(sFile);

            final StorageReference reference = storage.getReference().child("profile_picture")
                    .child(FirebaseAuth.getInstance().getUid());

            reference.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                    .child("profilepic").setValue(uri.toString());
                        }
                    });
                    Toast.makeText(SettingsActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}