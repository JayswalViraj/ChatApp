package com.example.chatzup.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.chatzup.Adapters.UsersAdapter;
import com.example.chatzup.Models.Users;
import com.example.chatzup.R;
import com.example.chatzup.databinding.FragmentChatsFragmentsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ChatsFragments extends Fragment {


    public ChatsFragments() {
        // Required empty public constructor
    }

    FragmentChatsFragmentsBinding binding;
    ArrayList<Users> list =new ArrayList<>();
    FirebaseDatabase database;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatsFragmentsBinding.inflate(inflater, container, false);

        database = FirebaseDatabase.getInstance();
        UsersAdapter adapter = new UsersAdapter(list , getContext());
        binding.chatsrecyclerview.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.chatsrecyclerview.setLayoutManager(layoutManager);

    database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

            list.clear();
        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
            Users users = dataSnapshot.getValue(Users.class);
            users.setUserid(dataSnapshot.getKey());
            if(!users.getUserid().equals(FirebaseAuth.getInstance().getUid()))
            {list.add(users);}


        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});




        return binding.getRoot();



    }
}