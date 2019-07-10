package com.example.deliverymotors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.deliverymotors.Captain.MainCaptainActivity;
import com.example.deliverymotors.Client.MainClientActivity;
import com.example.deliverymotors.Client.Pop_CaptainActivity;

import java.util.HashMap;

public class BidgeActivity extends AppCompatActivity {

    SessionManager sessionManager;
    Button start_but;

    @Override
    protected void onStart() {
        super.onStart();
        sessionManager =new SessionManager(BidgeActivity.this);
        sessionManager.checkLogin();
        HashMap<String,String> user=sessionManager.getUserDetail();
        String usertype=user.get(sessionManager.USERTYPE);
        if(usertype!=null){
            if (usertype.equals("client")){
                Intent i=new Intent(BidgeActivity.this, MainClientActivity.class);
                startActivity(i);
            }else if (usertype.equals("captain")){
                Intent i=new Intent(BidgeActivity.this, MainCaptainActivity.class);
                startActivity(i);
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bidge);


    }
}
