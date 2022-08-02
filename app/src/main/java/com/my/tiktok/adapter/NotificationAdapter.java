package com.my.tiktok.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.my.tiktok.R;
import com.my.tiktok.databinding.NotificationSampleBinding;
import com.my.tiktok.model.NotificationModel;
import com.my.tiktok.model.SignModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotificationAdapter  extends RecyclerView.Adapter<NotificationAdapter.viewHolder>{
    ArrayList<NotificationModel> list;
    Context context;

    public NotificationAdapter(ArrayList<NotificationModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.notification_sample,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        NotificationModel notificationModel=list.get(position);
      //  NotificationModel notificationModel=list.get(position);
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        final String notificationId=firebaseUser.getUid();
        String type=notificationModel.getType();
        FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(notificationModel.getNotificationBy()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SignModel user=snapshot.getValue(SignModel.class);
                Picasso.get()
                        .load(user.getCover())
                        .placeholder(R.drawable.photo)
                        .into(holder.binding.profileImage);

                if (type.equals("like"))
                {
                    holder.binding.notificationPhoto.setText(Html.fromHtml("<b>"+user.getName()+"</b>  "+  "liked  your post"));
                }
                else if (type.equals("comment"))
                {
                    holder.binding.notificationPhoto.setText(Html.fromHtml("<b>"+user.getName()+"</b>  "+  "comment on your post"));
                }
                else
                {
                    holder.binding.notificationPhoto.setText(Html.fromHtml("<b>"+user.getName()+"</b>  "+  "started following you"));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Are you sure");
                builder.setMessage("delete data cant ne undo");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "cancel", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class viewHolder extends RecyclerView.ViewHolder{
        NotificationSampleBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=NotificationSampleBinding.bind(itemView);
        }
    }
}
