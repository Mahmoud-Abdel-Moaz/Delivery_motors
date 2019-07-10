package com.example.deliverymotors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {
    Button continueCaptain,continueUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        continueCaptain=findViewById(R.id.continueCaptain);
        continueUser=findViewById(R.id.continueUser);
        continueCaptain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StartActivity.this, Login.class);
                i.putExtra("type","captain");
                startActivity(i);
            }
        });
        continueUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StartActivity.this, Login.class);
                i.putExtra("type","client");
                startActivity(i);
            }
        });
    }
}
