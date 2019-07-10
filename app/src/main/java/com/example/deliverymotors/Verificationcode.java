package com.example.deliverymotors;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.deliverymotors.Client.MainClientActivity;
import com.example.deliverymotors.Model.CaptainLocation;
import com.example.deliverymotors.Model.Client;
import com.example.deliverymotors.Model.ClientLocation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import java.util.concurrent.TimeUnit;

public class Verificationcode extends AppCompatActivity {
    EditText edit_vCode;
    Button but_supmit;
    private ProgressBar loading;
    Intent intent;
    String name,email,pass,phone,address,ssn,userType;
    private static String URL_REGIST="http://192.168.43.72/api/v1/clients/";
    Bitmap idBitmap,imgBitmap;

    private String mVerificationId;
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    String number;
    String userid;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificationcode);
        edit_vCode=findViewById(R.id.edit_vCode);
        but_supmit=findViewById(R.id.but_supmit);
     //   loading=findViewById(R.id.loading);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        intent=getIntent();

        name=intent.getStringExtra("name");
        phone=intent.getStringExtra("phone_number");
        email=intent.getStringExtra("mail");
        pass=intent.getStringExtra("password");
        address=intent.getStringExtra("address");
        ssn=intent.getStringExtra("ssn");
        userType=intent.getStringExtra("type");
        if (userType.equals("client")){
            URL_REGIST="http://192.168.43.72/api/v1/clients/";
        }else if(userType.equals("captain")){
            URL_REGIST="http://192.168.43.72/api/v1/drivers/";
        }

     //   imgBitmap = (Bitmap) intent.getParcelableExtra("profileImg");
   //     idBitmap = (Bitmap) intent.getParcelableExtra("ssnImg");

        number = "+20" + " " + phone.substring(1, 4) + " " + phone.substring(4, 7) + " " + phone.substring(7);
       sendVerificationCode(number);


        but_supmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = edit_vCode.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    edit_vCode.setError("Enter valid code");
                    edit_vCode.requestFocus();
                    return;
                }

                //verifying the code entered manually
                verifyVerificationCode(code);
            }
        });
    }
    private void sendVerificationCode(String no) {
        //  Toast.makeText(this, no, Toast.LENGTH_SHORT).show();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                no,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                edit_vCode.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(Verificationcode.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };

    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(Verificationcode.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            firebaseUser = mAuth.getCurrentUser();
                            assert firebaseUser != null;
                            final String phonenumber = firebaseUser.getPhoneNumber();
                            userid = firebaseUser.getUid();
                            Regist();


                        } else {
                            //verification unsuccessful.. display an error message
                            String message = "Somthing is wrong, we will fix it soon...";
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }
                        }
                    }
                });
    }

    private void Regist(){
     //   loading.setVisibility(View.VISIBLE);

        intent=getIntent();

        final String n=intent.getStringExtra("name");
        final String ph=intent.getStringExtra("phone_number");
        final String mail=intent.getStringExtra("email");
        final String pas=intent.getStringExtra("password");
        final String address=intent.getStringExtra("address");
        final String ss=intent.getStringExtra("ssn");
        final String type=intent.getStringExtra("type");
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response!=null){
                                SessionManager sessionManager =new SessionManager(Verificationcode.this);
                                String rid,rname,rphone,remail,raddress,rssn;
                                int rtotal_order;
                                double rate=0.0;
                                Intent intent=new Intent(Verificationcode.this,BidgeActivity.class);
                                //boolean has_copon;
                                JSONObject jsonObject=new JSONObject(response);
                                if (type.equals("client")){

                                    rid=jsonObject.getString("id");
                                    rname=jsonObject.getString("name");
                                    rphone=jsonObject.getString("phone_number");
                                    remail=jsonObject.getString("email");
                                    //  raddress=jsonObject.getString("");
                                    rssn=jsonObject.getString("snn");
                                    rtotal_order=jsonObject.getInt("total_order");
                                    ClientLocation clientLocation=new ClientLocation(rphone,rname,"defult",0.0,0.0);
                                    reference= FirebaseDatabase.getInstance().getReference("Client_locations").child(rphone);
                                    reference.setValue(clientLocation);


                                    sessionManager.createSesion(rname,rphone,remail,rssn,type,rid,String.valueOf(rtotal_order),String.valueOf(rate));
                                }else if(type.equals("captain")){
                                    rid=jsonObject.getString("id");
                                    rname=jsonObject.getString("name");
                                    rphone=jsonObject.getString("phone_number");
                                    remail=jsonObject.getString("email");
                                    //  raddress=jsonObject.getString("");
                                    rssn=jsonObject.getString("snn");
                                    rtotal_order=jsonObject.getInt("total_orders");
                                    rate=jsonObject.getInt("rate");
                                    CaptainLocation captainLocation=new CaptainLocation(rphone,rname,"defult",true,0.0,0.0,rate);
                                    reference= FirebaseDatabase.getInstance().getReference("Captain_locations").child(rphone);
                                    reference.setValue(captainLocation);

                                    sessionManager.createSesion(rname,rphone,remail,rssn,type,rid,String.valueOf(rtotal_order),String.valueOf(rate));
                                }
                                startActivity(intent);
                            }else {
                                Toast.makeText(Verificationcode.this, "Filed!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Verificationcode.this, "Register Error! "+e.toString(), Toast.LENGTH_SHORT).show();
                           // loading.setVisibility(View.GONE);

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(Verificationcode.this, "Register Error! "+error.toString(), Toast.LENGTH_SHORT).show();
                        //loading.setVisibility(View.GONE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                firebaseUser = mAuth.getCurrentUser();
                assert firebaseUser != null;
                userid = firebaseUser.getUid();
                Map<String,String> params=new HashMap<>();
                //params.put("id",userid);
                params.put("name",n);
                params.put("username",n);
                params.put("email",mail);
                params.put("password",pas);
                params.put("phone_number",ph);
              //  params.put("address",address);
                params.put("snn",ss);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}
