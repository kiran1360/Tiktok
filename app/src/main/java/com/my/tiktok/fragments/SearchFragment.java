package com.my.tiktok.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.my.tiktok.R;
import com.my.tiktok.adapter.SearchAdapter;
import com.my.tiktok.databinding.FragmentAddBinding;
import com.my.tiktok.databinding.FragmentSearchBinding;
import com.my.tiktok.model.SignModel;

import java.util.ArrayList;
import java.util.Queue;

public class SearchFragment extends Fragment {
    FragmentSearchBinding binding;
    FirebaseAuth auth;
    FirebaseStorage storage;
    FirebaseDatabase database;
    ArrayList<SignModel> list;
    public SearchFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        list=new ArrayList<>();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         binding=FragmentSearchBinding.inflate(inflater, container, false);

        /* binding.SearchButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
              String watchText=binding.searchTime.getText().toString();
             }
         });

        Queue queue= (Queue) database.getReference().child("Users")
                .orderByChild("name").startAt(binding.searchTime.getText().toString()).endAt(binding.searchTime.getText().toString()+"\uf8ff");
*/
        SearchAdapter adapter=new SearchAdapter(list,getContext());
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        binding.searchRecyle.setAdapter(adapter);
        binding.searchRecyle.setLayoutManager(layoutManager);

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    SignModel signModel=dataSnapshot.getValue(SignModel.class);
                    signModel.setuId(dataSnapshot.getKey());
                    list.add(signModel);
                 //  queue.add();
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

         return binding.getRoot();
    }
}