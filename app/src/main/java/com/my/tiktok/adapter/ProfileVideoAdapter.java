package com.my.tiktok.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.my.tiktok.R;
import com.my.tiktok.databinding.ProfileVideoSampleBinding;
import com.my.tiktok.model.PostModel;
import com.my.tiktok.model.SignModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfileVideoAdapter extends RecyclerView.Adapter<ProfileVideoAdapter.viewHolder> {
    ArrayList<PostModel> list;
    Context context;

    public ProfileVideoAdapter(ArrayList<PostModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.profile_video_sample,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        PostModel postModel=list.get(position);
        holder.binding.videoProfile.setVideoPath(postModel.getVideo());


        holder.binding.videoProfile.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                mp.start();






                //  isPlay=true;
            }

        });
        holder.binding.videoProfile.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                mp.start();
              //  isPlay=true;
            }
        });


        FirebaseDatabase.getInstance().getReference().child("Users")
                .child(postModel.getFollowingBy()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              if (snapshot.exists())
              {
                  SignModel signModel=snapshot.getValue(SignModel.class);
                  Picasso.get()
                          .load(signModel.getCover())
                          .placeholder(R.drawable.photo)
                          .into(holder.binding.profileImage);
              }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ProfileVideoSampleBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=ProfileVideoSampleBinding.bind(itemView);
        }
    }
}
