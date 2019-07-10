package com.example.deliverymotors;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.deliverymotors.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UsersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private UserAdapter userAdapter;
    private List<User> mUsers;
    DatabaseReference reference;
    SessionManager sessionManager;
    HashMap<String,String> userhash;
    String type,num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sessionManager=new SessionManager(this);
        userhash=sessionManager.getUserDetail();
        type=userhash.get(SessionManager.USERTYPE);
num=userhash.get(SessionManager.PHONE);
        mUsers=new ArrayList<>();
        readUsers();
    }
    public void readUsers(){

        if (type.equals("client")){
            reference= FirebaseDatabase.getInstance().getReference("Send_Orders");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        String capN=(String)snapshot.child("captainNumber").getValue();
                        String cliN=(String)snapshot.child("clientNumber").getValue();
                       if (cliN.equals(num)){
                            User user=new User(capN,capN);
                            mUsers.add(user);
                       }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }


            });
        }else if (type.equals("captain")){
            reference= FirebaseDatabase.getInstance().getReference("Send_Orders");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        String capN=(String)snapshot.child("captainNumber").getValue();
                        String cliN=(String)snapshot.child("clientNumber").getValue();
                        String cliName=(String)snapshot.child("clientName").getValue();
                        if (capN.equals(num)){
                            User user=new User(cliName,cliN);
                            mUsers.add(user);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }


            });
        }
        userAdapter = new UserAdapter(this,mUsers);
        recyclerView.setAdapter(userAdapter);

    }
}
