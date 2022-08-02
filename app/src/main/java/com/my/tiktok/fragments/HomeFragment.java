package com.my.tiktok.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.my.tiktok.adapter.VideoAdapter;
import com.my.tiktok.databinding.FragmentHomeBinding;
import com.my.tiktok.model.PostModel;
import com.my.tiktok.model.SignModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

  FragmentHomeBinding homeBinding;
  ArrayList<PostModel> list;
  FirebaseAuth auth;
  FirebaseDatabase database;
  FirebaseStorage storage;

    public HomeFragment() {
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
         homeBinding=FragmentHomeBinding.inflate(inflater, container, false);

        VideoAdapter videoAdapter=new VideoAdapter(list,getContext());
       // LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        homeBinding.pager2.setAdapter(videoAdapter);
       // homeBinding.pager2.setLayoutManager(layoutManager);

        database.getReference().child("video")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                            PostModel postModel=dataSnapshot.getValue(PostModel.class);
                            postModel.setVideId(dataSnapshot.getKey());
                         //   postModel.setVideId(dataSnapshot.getKey());
                            list.add(postModel);
                        }
                        videoAdapter.notifyDataSetChanged();
                        Log.i("TAG", "videoList: "+list.toString());


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        return  homeBinding.getRoot();


    }


}