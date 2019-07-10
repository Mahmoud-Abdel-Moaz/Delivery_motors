package com.example.deliverymotors.Captain;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.deliverymotors.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class CaptainReceiptOrderActivty extends AppCompatActivity {
    Intent intent;
    String orderId;
    DatabaseReference reference;
    String capNumber,cliNumber;
    String resltcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captain_receipt_order);
        final Activity activity=this;
        intent = getIntent();

        capNumber = intent.getStringExtra("capNumber");
        cliNumber = intent.getStringExtra("cliNumber");

        reference = FirebaseDatabase.getInstance().getReference("Send_Orders");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String capN = (String) snapshot.child("captainNumber").getValue();
                    String cliN = (String) snapshot.child("clientNumber").getValue();
                    if (capN.equals(capNumber) && cliN.equals(cliNumber)) {
                        orderId = snapshot.getKey();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        IntentIntegrator integrator=new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result !=null){
            if (result.getContents()==null){
                Toast.makeText(this, "You Cancelled the scanning", Toast.LENGTH_SHORT).show();
            }else {
                DatabaseReference   reference = FirebaseDatabase.getInstance().getReference()
                        .child("Send_Orders").child(result.getContents().toString());
                reference.removeValue();

            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }
}
