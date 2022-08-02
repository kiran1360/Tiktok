package com.my.tiktok.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.my.tiktok.EditProfileActivity2;
import com.my.tiktok.R;
import com.my.tiktok.adapter.ProfileVideoAdapter;
import com.my.tiktok.databinding.FragmentProfileBinding;
import com.my.tiktok.model.PostModel;
import com.my.tiktok.model.SignModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    FirebaseAuth auth;
    FirebaseStorage storage;
    FirebaseDatabase database;
    ActivityResultLauncher<String> launcher;
    ArrayList<PostModel> list;
    FirebaseUser currentUser;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        auth=FirebaseAuth.getInstance();
        list=new ArrayList<>();
        currentUser=auth.getCurrentUser();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentProfileBinding.inflate(inflater, container, false);
       /* binding.recyclerView.setAdapter(new PagerViewAdapter(getFragmentManager()));

        //  tabLayout=view.findViewById(R.id.tabLayout);
        binding.tabLayout.setupWithViewPager(binding.recyclerView);
      //  GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),GridLayoutManager.DEFAULT_SPAN_COUNT);



*/
        ProfileVideoAdapter profileVideoAdapter=new ProfileVideoAdapter(list,getContext());
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(), 3);
     //  LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        binding.recyclerView.setAdapter(profileVideoAdapter);
        binding.recyclerView.setLayoutManager(gridLayoutManager);

        database.getReference().child("video")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                            PostModel postModel=dataSnapshot.getValue(PostModel.class);
                            postModel.setVideId(dataSnapshot.getKey());
                            if (postModel.getFollowingBy().equals(FirebaseAuth.getInstance().getUid()))
                            {
                                list.add(postModel);
                            }
                            //   postModel.setVideId(dataSnapshot.getKey());
                           // list.add(postModel);
                        }
                        profileVideoAdapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

   /*   databaseReference=FirebaseDatabase.getInstance().getReference().getRef().child("likes");


      databaseReference.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
           if (snapshot.exists())
           {
               PostModel postModel=snapshot.getValue(PostModel.class);
               binding.postVideo.setText(postModel.getLikes()+"");
           }

          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });

*/
        


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

                   binding.userNM.setText(model.getName());
                   binding.userNumber.setText(model.getNumber());
                   binding.followersProfile.setText(model.getFollowersCount()+"");

               }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        launcher=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {

                binding.profileImage.setImageURI(uri);

                final StorageReference reference=storage.getReference().child("cover").child(FirebaseAuth.getInstance().getUid());
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                              database.getReference().child("Users").child(auth.getUid()).child("cover").setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                  @Override
                                  public void onSuccess(Void unused) {
                                      Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                                  }
                              });
                            }
                        });
                    }
                });
            }
        });

        binding.selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             launcher.launch("image/*");
            }
        });


        binding.settingg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    switch (v.getId())
                    {
                        case R.id.settingg:
                            auth.signOut();
                            Intent intent=new Intent(getContext(), SignActivity.class);
                            startActivity(intent);
                            break;


                }

            }
        });

     /*   binding.editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Edit_Profile.class);
                intent.putExtra("name",binding.userNM.getText().toString());
                intent.putExtra("num",binding.userNumber.getText().toString());
                startActivity(intent);
            }
        });*/

        binding.editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), EditProfileActivity2.class);
                intent.putExtra("name",binding.userNM.getText().toString());
                intent.putExtra("num",binding.userNumber.getText().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        return  binding.getRoot();
    }
}