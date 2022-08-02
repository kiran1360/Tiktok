package com.my.tiktok.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.my.tiktok.R;
import com.my.tiktok.databinding.EditSecondProfileBinding;
import com.my.tiktok.databinding.VideoSmpleBinding;
import com.my.tiktok.model.PostModel;

import java.util.ArrayList;

public class VideoEditAdapter extends RecyclerView.Adapter<VideoEditAdapter.viewHolder> {
    Context context;
    ArrayList<PostModel> list;

    public VideoEditAdapter(Context context, ArrayList<PostModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.edit_second_profile,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        PostModel postModel=list.get(position);
        holder.binding.videoRD.setVideoPath(postModel.getVideo());

        holder.binding.videoRD.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                mp.start();






                //  isPlay=true;
            }

        });
        holder.binding.videoRD.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                mp.start();
                //  isPlay=true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        EditSecondProfileBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=EditSecondProfileBinding.bind(itemView);
        }
    }
}
