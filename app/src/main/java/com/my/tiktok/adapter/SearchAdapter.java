package com.my.tiktok.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.my.tiktok.PressActivity2;
import com.my.tiktok.R;
import com.my.tiktok.databinding.SearchPageBinding;
import com.my.tiktok.model.FollowModel;
import com.my.tiktok.model.NotificationModel;
import com.my.tiktok.model.SignModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.viewHolder> {
    ArrayList<SignModel> list;
    Context context;

    public SearchAdapter(ArrayList<SignModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.search_page,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
     SignModel signModel=list.get(position);


        Picasso.get()
                .load(signModel.getCover())
                .placeholder(R.drawable.photo)
                .into(holder.binding.profileImage);
        holder.binding.searchPhoto.setText(signModel.getName());


        FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(signModel.getuId())
                .child("followers")
                .child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    holder.binding.searchId.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.search_bg));
                    holder.binding.searchId.setText("Following");
                    holder.binding.searchId.setTextColor(context.getResources().getColor(R.color.white));
                    holder.binding.searchId.setEnabled(false);
                }
                else {
                    holder.binding.searchId.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            FollowModel followModel=new FollowModel();
                            followModel.setFollowBy(FirebaseAuth.getInstance().getUid());
                            followModel.setFollowAt(new Date().getTime());

                            FirebaseDatabase.getInstance().getReference()
                                    .child("Users")
                                    .child(signModel.getuId())
                                    .child("followers")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .setValue(followModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("Users")
                                            .child(signModel.getuId())
                                            .child("followersCount")
                                            .setValue(signModel.getFollowersCount() + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            holder.binding.searchId.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.search_bg));
                                            holder.binding.searchId.setText("Following");
                                            holder.binding.searchId.setTextColor(context.getResources().getColor(R.color.white));
                                            holder.binding.searchId.setEnabled(false);
                                            Toast.makeText(context, "Follow You..", Toast.LENGTH_SHORT).show();




                                            NotificationModel model=new NotificationModel();
                                            model.setNotificationBy(FirebaseAuth.getInstance().getUid());
                                            model.setNotificationAt(new Date().getTime());
                                            model.setType("follow");

                                            FirebaseDatabase.getInstance().getReference()
                                                    .child("notification")
                                                    .child(signModel.getuId())
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

        holder.binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PressActivity2.class);
                intent.putExtra("name",signModel.getName());
                intent.putExtra("number",signModel.getNumber());
                intent.putExtra("fCount",signModel.getFollowersCount()+"");
                intent.putExtra("uid",signModel.getuId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class viewHolder extends RecyclerView.ViewHolder {
        SearchPageBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=SearchPageBinding.bind(itemView);
        }
    }
}
