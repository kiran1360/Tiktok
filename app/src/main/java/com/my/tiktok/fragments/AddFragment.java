package com.my.tiktok.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.my.tiktok.R;
import com.my.tiktok.databinding.FragmentAddBinding;
import com.my.tiktok.model.PostModel;
import com.my.tiktok.model.SignModel;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class AddFragment extends Fragment {
FragmentAddBinding binding;
FirebaseAuth auth;
FirebaseStorage storage;
FirebaseDatabase database;
ActivityResultLauncher<String> launcher;
Uri uri;
MediaController mediaController;
boolean isVisible=true;
ProgressDialog dialog;

    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();

        dialog=new ProgressDialog(getContext());

        dialog.setTitle("loading..");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait..");




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAddBinding.inflate(inflater, container, false);


         database.getReference().child("Users").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {

                 if (snapshot.exists())
                 {
                     SignModel model=snapshot.getValue(SignModel.class);

                     Picasso.get()
                             .load(model.getCover())
                             .placeholder(R.drawable.photo)
                             .into(binding.profileImage);
                     binding.searchPhoto.setText(model.getName());
                 }

             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });

       /* launcher=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {

              binding.videoView.setVideoURI(uri);

            }
        });*/

       /* binding.uploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch("video/*");
            }
        });*/

        binding.uploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
               intent.setAction(Intent.ACTION_GET_CONTENT);
               intent.setType("video/*");
               startActivityForResult(intent,10);
            }
        });

      /*  binding.appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final StorageReference reference=storage.getReference().child("video").child(FirebaseAuth.getInstance().getUid())
                        .child(new Date().getTime()+"");
                reference.putFile(uri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {


                                PostModel postModel=new PostModel(uri1.toString());


                                postModel.setFollowingBy(FirebaseAuth.getInstance().getUid());
                                postModel.setDesc(binding.descriptionPost.getText().toString());
                                postModel.setFollowingAt(new Date().getTime()+"");

                                database.getReference().child("posts")
                                        .push()
                                        .setValue(postModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                      //  dialog.dismiss();
                                        Toast.makeText(getContext(), "Posted successfully", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        });*/
/*
                    }
                });*/
     /*       }
        });*/
        binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (isVisible)
                {
                    isVisible=false;
                    binding.videoView.pause();
                }
                binding.videoView.start();
                isVisible=true;


            }
        });
        binding.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                binding.videoView.resume();
                isVisible=false;
            }
        });

        binding.appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

                final StorageReference reference=storage.getReference().child(FirebaseAuth.getInstance().getUid())
                        .child(new Date().getTime()+"");
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {



                                PostModel postModel=new PostModel();
                                postModel.setVideo(uri.toString());
                                postModel.setFollowingBy(FirebaseAuth.getInstance().getUid());
                                postModel.setDesc(binding.descriptionPost.getText().toString());
                                postModel.setFollowingAt(new Date().getTime()+"");
                                Log.i("TAG", "Dateforat: " + new Date().getTime()+"");


                                database.getReference()
                                        .child("video")
                                        .push()
                                        .setValue(postModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getContext(), "Video posted", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });


                            }
                        });
                    }
                });
            }
        });

        return  binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() !=null)
        {
            uri=data.getData();
            binding.videoView.setVideoURI(uri);
          //  binding.videoView.setVideoURI(uri);
           //  binding.videoView.requestFocus();
           // binding.videoView.start();
          // mediaController.setAnchorView(binding.videoView);
        binding.videoView.setMediaController(mediaController);
       binding.videoView.start();

         //   binding.videoView.setVisibility(View.VISIBLE);
          //  binding.imageView2.setVisibility(View.VISIBLE);


        }
    }
        }

