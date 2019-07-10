package com.example.deliverymotors.Client;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliverymotors.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class ClientReceiptOrderActivity extends AppCompatActivity {
    TextView txt_price;
    Button but_done;
    ImageView img_qr;
    Intent intent;
    String orderId;
    DatabaseReference reference;
    String price,capNumber,cliNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_receipt_order);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Receve Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_price=findViewById(R.id.txt_price);
        but_done=findViewById(R.id.but_done);
        img_qr=findViewById(R.id.img_qr);
        intent=getIntent();
        price=intent.getStringExtra("price");
        capNumber=intent.getStringExtra("capNumber");
        cliNumber=intent.getStringExtra("cliNumber");

      reference= FirebaseDatabase.getInstance().getReference("Send_Orders");
      reference.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                  String capN=(String)snapshot.child("captainNumber").getValue();
                  String cliN=(String)snapshot.child("clientNumber").getValue();
                  if (capN.equals(capNumber)&&cliN.equals(cliNumber)){
                      orderId=snapshot.getKey();
                  }
              }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });

        MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
        try{
            BitMatrix bitMatrix=multiFormatWriter.encode(orderId, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder=new BarcodeEncoder();
            Bitmap bitmap=barcodeEncoder.createBitmap(bitMatrix);
            img_qr.setImageBitmap(bitmap);
        }
        catch (WriterException e)
        {
            e.printStackTrace();
        }

        but_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference= FirebaseDatabase.getInstance().getReference("Send_Orders").child(orderId);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            Toast.makeText(ClientReceiptOrderActivity.this, "The Order isn't Recved Yet", Toast.LENGTH_SHORT).show();
                        }else  if (!dataSnapshot.exists()){
                            Toast.makeText(ClientReceiptOrderActivity.this, "Thanke You For Using Us", Toast.LENGTH_SHORT).show();
                        finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
