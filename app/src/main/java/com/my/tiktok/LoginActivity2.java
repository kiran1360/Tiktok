package com.my.tiktok;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.my.tiktok.databinding.ActivityLogin2Binding;

public class LoginActivity2 extends AppCompatActivity {
   ActivityLogin2Binding binding;
   FirebaseAuth auth;
   FirebaseDatabase database;
   FirebaseStorage storage;
   FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLogin2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        currentUser=auth.getCurrentUser();

        binding.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=binding.edit3.getText().toString();
                String password=binding.edit4.getText().toString();
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful())
                       {
                           Toast.makeText(LoginActivity2.this, "User Login", Toast.LENGTH_SHORT).show();
                           Intent intent=new Intent(LoginActivity2.this,MainActivity.class);
                           startActivity(intent);
                       }
                       else {
                           Toast.makeText(LoginActivity2.this, "User Not Found", Toast.LENGTH_SHORT).show();
                       }
                    }
                });
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currentUser!=null)
        {
            Intent intent=new Intent(LoginActivity2.this,MainActivity.class);
            startActivity(intent);
        }

    }
    /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (currentUser!=null)
        {

        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/
}