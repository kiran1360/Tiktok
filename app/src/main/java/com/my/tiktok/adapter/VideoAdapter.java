package com.my.tiktok.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.my.tiktok.ChatActivity2;
import com.my.tiktok.PressActivity2;
import com.my.tiktok.R;
import com.my.tiktok.databinding.VideoSmpleBinding;
import com.my.tiktok.model.NotificationModel;
import com.my.tiktok.model.PostModel;
import com.my.tiktok.model.SignModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.viewHolder> {
    ArrayList<PostModel> list;
    Context context;
   boolean isPlay=false;
   boolean isAdd=false;
    Uri uri;

    public VideoAdapter(ArrayList<PostModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.video_smple,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
         PostModel postModel = list.get(position);
        uri=Uri.parse(list.get(position).getVideo());
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        final String userId=firebaseUser.getUid();


        FirebaseDatabase.getInstance().getReference()
                .child("video")
                .child(postModel.getVideId())
                .child("like").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(postModel.getVideId()).hasChild(userId))
                {
                    int likes=(int)snapshot.child(postModel.getVideId()).getChildrenCount();
                    holder.binding.likeText.setText(likes+"likes");
                    holder.binding.likeText.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_fav,0,0);

                }
                else
                {
                    int likes=(int)snapshot.child(postModel.getVideId()).getChildrenCount();
                    holder.binding.likeText.setText(likes+"likes");
                    holder.binding.likeText.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_baseline_favorite_border_24,0,0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.binding.likeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isAdd=true;
                FirebaseDatabase.getInstance().getReference()
                        .child("video")
                        .child(postModel.getVideId())
                        .child("like").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (isAdd==true)
                        {
                            if (snapshot.child(postModel.getVideId()).hasChild(userId))
                            {
                                FirebaseDatabase.getInstance().getReference()
                                        .child("video")
                                        .child(postModel.getVideId())
                                        .child("like")
                                       .child(postModel.getVideId()).child(userId).removeValue();
                                isAdd=false;
                            }
                            else {
                                FirebaseDatabase.getInstance().getReference()
                                        .child("video")
                                        .child(postModel.getVideId())
                                        .child("like")
                                        .child(postModel.getVideId()).child(userId).setValue(true);
                                isAdd=false;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        holder.binding.descriptionUser.setText(postModel.getDesc());
        holder.binding.video.setVideoPath(postModel.getVideo());
        holder.binding.likeText.setText(postModel.getLikes()+"");
        holder.binding.commentText.setText(postModel.getCommentCount()+"");



        holder.binding.shareText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("video/*");
               intent.putExtra(Intent.EXTRA_STREAM,uri);
               // intent.putExtra(Intent.EXTRA_STREAM, uri);
                context.startActivity(Intent.createChooser(intent,"Share Video Via"));

            }
        });

        holder.binding.video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {

                    mp.start();
                 isPlay=true;
                }

            });
            holder.binding.video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                    mp.start();
                //    mp.setVolume(0,0);
                    isPlay=true;
                }
            });

            holder.binding.video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isPlay)
                    {
                       holder.binding.video.pause();
                      //  mp.setVolume(0,0);
                        isPlay=false;

                    }
                    else
                    {
                        holder.binding.video.resume();
                        isPlay=true;
                    }
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
                 holder.binding.userName.setText(signModel.getName());
             }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






/*

        FirebaseDatabase.getInstance().getReference().child("video")
                .child(postModel.getVideId())
                .child("like")
                .child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    holder.binding.likeText.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_fav,0,0);
                }
                else
                {
                    holder.binding.likeText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseDatabase.getInstance().getReference()
                                    .child("video")
                                    .child(postModel.getVideId())
                                    .child("like")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("video")
                                            .child(postModel.getVideId())
                                            .child("likes")

                                            .setValue(postModel.getLikes() +1 ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            holder.binding.likeText.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_fav,0,0);

                                            NotificationModel model=new NotificationModel();
                                            model.setNotificationBy(FirebaseAuth.getInstance().getUid());
                                            model.setNotificationAt(new Date().getTime());
                                            model.setPostID(postModel.getVideId());
                                            model.setPostedBy(postModel.getFollowingBy());
                                            model.setType("like");


                                            FirebaseDatabase.getInstance().getReference()
                                                    .child("notification")
                                                    .child(postModel.getVideId())
                                                    .push()
                                                    .setValue(model);
                                        }
                                    });

                                }
                            });


                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



*/



/*

FirebaseDatabase.getInstance().getReference().child("video")
        .child(postModel.getVideId())
        .child("Like")
        .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists())
                {
                    holder.binding.likeText.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_fav,0,0);
                 // isAdd=false;
                }
                else
                {
                    holder.binding.likeText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseDatabase.getInstance().getReference().child("video")
                                    .child(postModel.getVideId())
                                    .child("Like")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("video")
                                            .child(postModel.getVideId())
                                            .child("likes")
                                            .setValue(postModel.getLikes() + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            holder.binding.likeText.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_fav,0,0);
                                          //  isAdd=true;
                                            NotificationModel model=new NotificationModel();
                                            model.setNotificationBy(FirebaseAuth.getInstance().getUid());
                                            model.setNotificationAt(new Date().getTime());
                                            model.setPostID(postModel.getVideId());
                                            model.setPostedBy(postModel.getFollowingBy());
                                            model.setType("like");


                                            FirebaseDatabase.getInstance().getReference()
                                                    .child("notification")
                                                    .child(postModel.getFollowingBy())
                                                    .push()
                                                    .setValue(model);
                                        }
                                    });

                                }
                            });

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

*/









        holder.binding.commentText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ChatActivity2.class);
                intent.putExtra("Id",postModel.getVideId());
                intent.putExtra("postedBy",postModel.getFollowingBy());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });



/*
        holder.binding.shareText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse(list.get(position).getVideo());
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("video/*");
                intent.putExtra(Intent.EXTRA_STREAM,uri);
                context.startActivity(Intent.createChooser(intent,"Share Video Via"));

            }
        });*/

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        VideoSmpleBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=VideoSmpleBinding.bind(itemView);
        }
    }
}
