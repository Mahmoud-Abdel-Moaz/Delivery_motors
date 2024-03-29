package com.example.deliverymotors;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliverymotors.Adapter.MessageAdapter;
import com.example.deliverymotors.Model.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    CircleImageView profile_image;
    TextView username;


    DatabaseReference reference;
    ImageButton btn_send;
    EditText text_send;

    MessageAdapter messageAdapter;
    List<Chat> mChat;

    RecyclerView recyclerView;

    Intent intent;
    SessionManager sessionManager;
    HashMap<String,String> userhash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        sessionManager=new SessionManager(this);
        userhash=sessionManager.getUserDetail();
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        profile_image=findViewById(R.id.profile_image);
        username=findViewById(R.id.username);
        btn_send=findViewById(R.id.btn_send);
        text_send=findViewById(R.id.text_send);

        intent =getIntent();
        final String userid=intent.getStringExtra("userid");

        final String fuser= userhash.get(SessionManager.PHONE);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=text_send.getText().toString();
                if (!msg.equals("")){
                    sendMessage(fuser,userid,msg);
                }else{
                    Toast.makeText(MessageActivity.this, "You can,t send empty message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });


    }
    private void sendMessage(String sender,String recever,String message){

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("recever",recever);
        hashMap.put("message",message);
        reference.child("Chats").push().setValue(hashMap);
    }

    private void readMessages(final String myid, final String userid, final String imageurl){
        mChat = new ArrayList<>();

        reference =FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Chat chat=new Chat();
                    // Chat chat = snapshot.getValue(Chat.class);

                    chat.setSender((String)snapshot.child("sender").getValue());
                    chat.setReceiver((String)snapshot.child("recever").getValue());
                    chat.setMessage((String)snapshot.child("message").getValue());

                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){
                        mChat.add(chat);
                    }

                    messageAdapter=new MessageAdapter(MessageActivity.this,mChat,imageurl);
                    recyclerView.setAdapter(messageAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
