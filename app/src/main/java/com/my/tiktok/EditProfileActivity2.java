package com.my.tiktok;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.my.tiktok.databinding.ActivityEditProfile2Binding;
import com.my.tiktok.model.PostModel;


public class EditProfileActivity2 extends AppCompatActivity {
ActivityEditProfile2Binding binding;
FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEditProfile2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String name=getIntent().getStringExtra("name");
        String UserID=getIntent().getStringExtra("num");

        binding.userEdit.setText(name);
        binding.editNumber.setText(UserID);


        database=FirebaseDatabase.getInstance();









    }
}