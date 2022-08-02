package com.my.tiktok;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.my.tiktok.adapter.CommentAdapter;
import com.my.tiktok.databinding.ActivityChat2Binding;
import com.my.tiktok.model.CommentModel;
import com.my.tiktok.model.NotificationModel;

import java.util.ArrayList;
import java.util.Date;

public class ChatActivity2 extends AppCompatActivity {
    ActivityChat2Binding binding;
   public static String postId;
   public static String postedBy;
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage storage;
    ArrayList<CommentModel> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChat2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        list=new ArrayList<>();
        CommentAdapter commentAdapter=new CommentAdapter(list,this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.postRecyle.setLayoutManager(layoutManager);
        binding.postRecyle.setAdapter(commentAdapter);

        postId=getIntent().getStringExtra("Id");
        postedBy=getIntent().getStringExtra("postedBy");
       /* String user = auth.getCurrentUser();
        var signupDate = new Date(user.metadata.creationTime);*/


        database.getReference().child("video")
                .child(postId).child("comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    CommentModel commentModel=dataSnapshot.getValue(CommentModel.class);
                    list.add(commentModel);

                }
                commentAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        binding.postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=binding.textComment.getText().toString();

                Date date=new Date();
                CommentModel comment=new CommentModel();
                comment.setPostBody(text);
                comment.setPostedBy(FirebaseAuth.getInstance().getUid());
                comment.setPostedAt(date.getTime());


                database.getReference().child("video")
                        .child(postId)
                        .child("comments")
                        .push()
                        .setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference().child("video")
                                .child(postId)
                                .child("commentCount").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int commentCount=0;
                                if (snapshot.exists())
                                {
                                    commentCount =snapshot.getValue(Integer.class);

                                }
                                database.getReference().child("video")
                                        .child(postId)
                                        .child("commentCount")
                                        .setValue(commentCount + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        binding.textComment.setText(" ");
                                        Toast.makeText(ChatActivity2.this, "commented", Toast.LENGTH_SHORT).show();

                                        NotificationModel model=new NotificationModel();
                                        model.setNotificationBy(FirebaseAuth.getInstance().getUid());
                                        model.setNotificationAt(new Date().getTime());
                                        model.setPostID(postId);
                                        model.setPostedBy(postedBy);
                                        model.setType("comment");

                                        FirebaseDatabase.getInstance().getReference()
                                                .child("notification")
                                                .child(postedBy)
                                                .push()
                                                .setValue(model);
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
            }
        });



    }
}