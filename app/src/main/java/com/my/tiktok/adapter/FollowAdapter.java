package com.my.tiktok.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.my.tiktok.R;
import com.my.tiktok.databinding.NotificationSampleBinding;
import com.my.tiktok.databinding.SearchPageBinding;
import com.my.tiktok.model.FollowModel;

import java.util.ArrayList;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.viewHolder>{
    ArrayList<FollowModel> list;
    Context context;

    public FollowAdapter(ArrayList<FollowModel> list, Context context) {
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
        FollowModel followModel=list.get(position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        SearchPageBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=SearchPageBinding.bind(itemView);
        }
    }
}
