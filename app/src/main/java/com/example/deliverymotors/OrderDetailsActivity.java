package com.example.deliverymotors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class OrderDetailsActivity extends AppCompatActivity {
    TextView txt_who,txt_name,txt_from,txt_to,txt_startDate,txt_endDate,txt_price,txt_order;
    Intent intent;
    SessionManager sessionManager;
    HashMap<String,String> user;
    String type,name,from,to,startDate,endDate,price,order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Order Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        intent=getIntent();

        txt_who=findViewById(R.id.txt_who);
        txt_name=findViewById(R.id.txt_name);
        txt_from=findViewById(R.id.txt_from);
        txt_to=findViewById(R.id.txt_to);
        txt_startDate=findViewById(R.id.txt_startDate);
        txt_endDate=findViewById(R.id.txt_endDate);
        txt_price=findViewById(R.id.txt_price);
        txt_order=findViewById(R.id.txt_order);
        sessionManager=new SessionManager(this);
        user=sessionManager.getUserDetail();
        type=user.get(SessionManager.USERTYPE);
        name=intent.getStringExtra("name");
        from=intent.getStringExtra("from");
        to=intent.getStringExtra("to");
        startDate=intent.getStringExtra("startDate");
        endDate=intent.getStringExtra("endDate");
        price=intent.getStringExtra("price");
        order=intent.getStringExtra("order");
        if (type.equals("client")){
            txt_who.setText("Captain");
        }else if(type.equals("Captain")){
            txt_who.setText("Client");
        }
        txt_name.setText(name);
        txt_from.setText(from);
        txt_to.setText(to);
        txt_startDate.setText(startDate);
        txt_price.setText(price);
        txt_order.setText(order);


    }
}
