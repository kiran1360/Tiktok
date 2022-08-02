package com.my.tiktok;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.my.tiktok.fragments.AddFragment;
import com.my.tiktok.fragments.HomeFragment;
import com.my.tiktok.fragments.NotificationFragment;
import com.my.tiktok.fragments.ProfileFragment;
import com.my.tiktok.fragments.SearchFragment;
import com.my.tiktok.fragments.SignActivity;
import com.my.tiktok.model.SignModel;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
  BottomNavigationView bottomNavigationView;
  FirebaseAuth auth;
  FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        bottomNavigationView=findViewById(R.id.bottomNav);
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,new HomeFragment());
        transaction.commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                switch (item.getItemId())
                {
                    case R.id.home:
                        transaction.replace(R.id.container,new HomeFragment());
                      //  Toast.makeText(MainActivity.this, "Click Home", Toast.LENGTH_SHORT).show();
                       // return true;
                        break;
                    case R.id.search:
                        transaction.replace(R.id.container,new SearchFragment());
                      //  Toast.makeText(MainActivity.this, "Click Search", Toast.LENGTH_SHORT).show();
                      //  return true;
                        break;
                    case R.id.add:
                        transaction.replace(R.id.container,new AddFragment());
                      //  Toast.makeText(MainActivity.this, "Click Add", Toast.LENGTH_SHORT).show();
                      //  return true;
                        break;
                    case R.id.notification:
                        transaction.replace(R.id.container,new NotificationFragment());
                         /* database.getReference().child("Users")
                                .child(FirebaseAuth.getInstance().getUid())
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists())
                                        {
                                            SignModel model=snapshot.getValue(SignModel.class);
                                            Picasso.get()
                                                    .load(model.getCover())
                                                    .placeholder(R.drawable.photo);

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });*/
                        //  Toast.makeText(MainActivity.this, "Click Profile", Toast.LENGTH_SHORT).show();
                        // return true;
                       // Toast.makeText(MainActivity.this, "Click Notification", Toast.LENGTH_SHORT).show();
                      //  return true;
                        break;

                    case R.id.profile:
                        transaction.replace(R.id.container,new ProfileFragment());
                        break;


                }
                transaction.commit();
                return true;

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.second_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.one:
                auth.signOut();
                Intent intent=new Intent(MainActivity.this, SignActivity.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}