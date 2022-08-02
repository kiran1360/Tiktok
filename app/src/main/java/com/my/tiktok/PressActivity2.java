package com.my.tiktok;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.my.tiktok.adapter.VideoEditAdapter;
import com.my.tiktok.databinding.ActivityPress2Binding;
import com.my.tiktok.model.PostModel;
import com.my.tiktok.model.SignModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PressActivity2 extends AppCompatActivity {
  ActivityPress2Binding binding;
  FirebaseAuth auth;
  FirebaseDatabase database;
  ArrayList<PostModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPress2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        list=new ArrayList<>();
     //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setTitle("");
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        String uName=getIntent().getStringExtra("name");
        String uNumber=getIntent().getStringExtra("number");
        String uFollowers=getIntent().getStringExtra("fCount");
        String uid=getIntent().getStringExtra("uid");


      /*  binding.userPress.setText(uName);
        binding.pressNumber.setText(uNumber);
        binding.followersPress.setText(uFollowers);*/

        database.getReference().child("Users")
                .child(uid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        SignModel model=snapshot.getValue(SignModel.class);
                        Picasso.get()
                                .load(model.getCover())
                                .placeholder(R.drawable.photo)
                                .into(binding.profileImage);
                        binding.userPress.setText(model.getName());
                        binding.pressNumber.setText(model.getNumber());
                        binding.followersPress.setText(model.getFollowersCount()+"");
                        binding.newId.setText(model.getNumber());


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        VideoEditAdapter videoEditAdapter=new VideoEditAdapter(this,list);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3);
        binding.recyclerViewPress.setAdapter(videoEditAdapter);
        binding.recyclerViewPress.setLayoutManager(gridLayoutManager);

        database.getReference().child("video")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot:snapshot.getChildren())
                        {
                            PostModel postModel=dataSnapshot.getValue(PostModel.class);
                            list.add(postModel);
                        }

                        videoEditAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



    }

   /* @Override
    public void onBackPressed() {
        onBackPressed();
       finish();
    }*/
}