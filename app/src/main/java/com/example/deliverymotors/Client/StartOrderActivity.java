package com.example.deliverymotors.Client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.deliverymotors.BidgeActivity;
import com.example.deliverymotors.Model.SendOrder;
import com.example.deliverymotors.R;
import com.example.deliverymotors.SessionManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class StartOrderActivity extends AppCompatActivity {
    EditText edit_from,edit_to,edit_order;
    Button but_sandorder;
    Intent intent;
    String CapNumber,CapName;
    SessionManager sessionManager;
    HashMap<String,String> userhash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_order);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Start Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sessionManager =new SessionManager(this);
        userhash=sessionManager.getUserDetail();
        edit_from=findViewById(R.id.edit_from);
        edit_order=findViewById(R.id.edit_order);
        edit_to=findViewById(R.id.edit_to);
        but_sandorder=findViewById(R.id.but_sandorder);
        intent=getIntent();
        CapName=intent.getStringExtra("CapName");
        CapNumber=intent.getStringExtra("CapNumber");
        but_sandorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String order=edit_order.getText().toString().trim();
                String strTo=edit_to.getText().toString().trim();
                String strFrom=edit_from.getText().toString().trim();
                sandorder(order,strTo,strFrom);
            }
        });

    }
    private void sandorder(String order,String strTo,String strFrom){

        if (order.isEmpty()) {
            edit_order.setError("It,s required ");
            edit_order.requestFocus();
        }
        if (strTo.isEmpty()) {
            edit_to.setError("It,s required ");
            edit_to.requestFocus();
        }

        if (strFrom.isEmpty()) {
            edit_from.setError("It,s required ");
            edit_from.requestFocus();
        }
        if (order.isEmpty()||strTo.isEmpty()||strFrom.isEmpty()){
            return;
        }
        String clientName=userhash.get(SessionManager.NAME);
        String clientNamber=userhash.get(SessionManager.PHONE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String key = database.getReference("Send_Orders").push().getKey();
        SendOrder sendOrder=new SendOrder(order,clientNamber,String.valueOf(userhash.get(SessionManager.ID)),CapNumber,clientName,strTo,strFrom);
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Send_Orders").child(key);
        reference.setValue(sendOrder);
        Toast.makeText(this, "Order Send Successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}
