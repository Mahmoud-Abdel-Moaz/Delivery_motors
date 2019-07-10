package com.example.deliverymotors.Client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.deliverymotors.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Pop_CaptainActivity extends AppCompatActivity {

    CircleImageView cap_img;
    TextView txt_name;
    RatingBar ratingBar;
    Button but_sendOrder,but_cansel;
    Intent intent;
    String CaoName,CaoNamner,CaoImage;
    Double CaoRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop__captain);

        cap_img=findViewById(R.id.cap_img);
        txt_name=findViewById(R.id.txt_name);
        ratingBar=findViewById(R.id.ratingBar);
        but_sendOrder=findViewById(R.id.but_sendOrder);
        but_cansel=findViewById(R.id.but_cansel);
        but_cansel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        intent=getIntent();
        CaoName=intent.getStringExtra("CaoName");
        CaoNamner=intent.getStringExtra("CaoNamner");
        CaoImage=intent.getStringExtra("CaoImage");
        CaoRate=Double.parseDouble(intent.getStringExtra("CaoRate"));

        txt_name.setText(CaoName);

        but_sendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Pop_CaptainActivity.this,StartOrderActivity.class);
                i.putExtra("CapName",CaoName);
                i.putExtra("CapNumber",CaoNamner);
                startActivity(i);
            }
        });

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*.7),(int)(height*.6));

        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x=0;
        params.y=-20;

        getWindow().setAttributes(params);

    }
}
