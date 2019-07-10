package com.example.deliverymotors.Client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.deliverymotors.Model.Volly;
import com.example.deliverymotors.R;
import com.example.deliverymotors.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Profile extends AppCompatActivity {
    TextView txt_username,txt_email,txt_phone,txt_ssn,txt_address;
    HashMap<String,String> userhash;
    ImageView img_profile;
    SessionManager sessionManager;
    private static String URL_Get="http://192.168.43.72 /api/v1/clients/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txt_username=findViewById(R.id.txt_username);
        txt_email=findViewById(R.id.txt_email);
        txt_phone=findViewById(R.id.txt_phone);
        txt_ssn=findViewById(R.id.txt_ssn);
        txt_address=findViewById(R.id.txt_address);
        img_profile=findViewById(R.id.img_profile);
        sessionManager=new SessionManager(this);
        userhash=sessionManager.getUserDetail();

        txt_username.setText("Name: "+userhash.get(SessionManager.NAME));
        txt_email.setText("Email: "+userhash.get(SessionManager.EMAIL));
        txt_phone.setText("Phone: "+userhash.get(SessionManager.PHONE));
        txt_ssn.setText("National Id: "+userhash.get(SessionManager.SSN));
        txt_address.setText("Address: El Sadat");


        //getjson();

    }
    public void getjson(){
        JsonObjectRequest requst =new JsonObjectRequest(Request.Method.GET, URL_Get, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response!=null){
                            try {

                                String rname,remail;
                                rname=response.getString("name");
                                remail=response.getString("email");
                                txt_email.setText(remail);
                                txt_username.setText(rname);

                            }catch (JSONException e){
                                e.printStackTrace();
                                Toast.makeText(Profile.this, "Register Error! "+e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(Profile.this, "Field", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(Profile.this, "Register Error! "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        Volly.getinsance(getApplicationContext()).add_request(requst);
    }
}
