package com.my.tiktok.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.github.marlonlom.utilities.timeago.TimeAgoMessages;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.my.tiktok.R;
import com.my.tiktok.databinding.CommentSampleBinding;
import com.my.tiktok.model.CommentModel;
import com.my.tiktok.model.PostModel;
import com.my.tiktok.model.SignModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Queue;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.viewHolder>{
    ArrayList<CommentModel> list;
    Context context;
    DatabaseReference reference;

    public CommentAdapter(ArrayList<CommentModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.comment_sample,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

      /*  Picasso.get()
                .load(signModel.getCover())
                .placeholder(R.drawable.photo)
                .into(holder.binding.profileImage);*/


        CommentModel commentModel=list.get(position);
       // holder.binding.commentPhoto.setText(commentModel.getPostBody());
            Calendar calendar=Calendar.getInstance();
      //  Long Timestamp = 1633304782;
      /*  var user = firebase.auth().currentUser;
        var signupDate = new Date(user.metadata.creationTime);*/
       Date timeD = new Date(commentModel.getPostedAt() * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss",Locale.getDefault());

        String Time = sdf.format(timeD);

        holder.binding.time.setText(Time);



        //    String text = TimeAgo.using(commentModel.getPostedAt());

       // holder.binding.time.setText(commentModel.getPostedAt()+"");
        FirebaseDatabase.getInstance().getReference().child("Users")
                .child(commentModel.getPostedBy()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SignModel signModel=snapshot.getValue(SignModel.class);
                Picasso.get()
                        .load(signModel.getCover())
                        .placeholder(R.drawable.photo)
                        .into(holder.binding.profileImage);
                holder.binding.commentPhoto.setText(Html.fromHtml("<b>"+ signModel.getName() +"</b>"+"  "+commentModel.getPostBody()));





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("are you sure");
                builder.setMessage("okk");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference("video")
                                .chil

                    }
                });

                builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                builder.show();
            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class viewHolder extends RecyclerView.ViewHolder{
        CommentSampleBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=CommentSampleBinding.bind(itemView);
        }
    }
}
