package com.my.tiktok.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.my.tiktok.R;
import com.my.tiktok.adapter.NotificationAdapter;
import com.my.tiktok.databinding.FragmentNotificationBinding;
import com.my.tiktok.model.NotificationModel;

import java.util.ArrayList;

public class NotificationFragment extends Fragment {

    FragmentNotificationBinding binding;
    ArrayList<NotificationModel> list;
    FirebaseAuth auth;
    FirebaseStorage storage;
    FirebaseDatabase database;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list=new ArrayList<>();
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentNotificationBinding.inflate(inflater, container, false);
        NotificationAdapter notificationAdapter=new NotificationAdapter(list,getContext());
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        binding.notificationRecyle.setAdapter(notificationAdapter);
        binding.notificationRecyle.setLayoutManager(layoutManager);

   database.getReference().child("notification")
           .child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
       @Override
       public void onDataChange(@NonNull DataSnapshot snapshot) {
           for (DataSnapshot dataSnapshot:snapshot.getChildren())
           {
               NotificationModel notificationModel=dataSnapshot.getValue(NotificationModel.class);
               notificationModel.setNotificationID(dataSnapshot.getKey());
               list.add(notificationModel);
           }
           notificationAdapter.notifyDataSetChanged();
       }

       @Override
       public void onCancelled(@NonNull DatabaseError error) {

       }
   });


        return  binding.getRoot();

    }
}