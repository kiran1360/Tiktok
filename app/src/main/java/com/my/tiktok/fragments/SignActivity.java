package com.my.tiktok.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.my.tiktok.LoginActivity2;
import com.my.tiktok.MainActivity;
import com.my.tiktok.R;
import com.my.tiktok.databinding.ActivitySignBinding;
import com.my.tiktok.model.SignModel;

public class SignActivity extends AppCompatActivity {
    ActivitySignBinding binding;
    FirebaseAuth auth;
    FirebaseStorage storage;
    FirebaseDatabase database;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();
        currentUser=auth.getCurrentUser();


        binding.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=binding.edit3.getText().toString(); String password=binding.edit4.getText().toString();
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            SignModel signModel=new SignModel(binding.edit1.getText().toString(),binding.edit2.getText().toString(),email,password);
                            String id=task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(signModel);

                            Toast.makeText(SignActivity.this, "User Successfully Register...", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(SignActivity.this,MainActivity.class);
                            startActivity(intent);

                        }
                        else
                        {
                            Toast.makeText(SignActivity.this, "User Not Register", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });





binding.choice.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(SignActivity.this, LoginActivity2.class);
        startActivity(intent);
    }
});
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currentUser!=null)
        {
            Intent intent=new Intent(SignActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }
}