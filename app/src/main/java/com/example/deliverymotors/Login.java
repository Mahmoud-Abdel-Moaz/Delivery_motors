package com.example.deliverymotors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText edit_phone,edit_pass;
    Button but_login;
    TextView txt_signUp;
    Intent intent;
    String userType;
    private static String URL_LOGIN="http://192.168.43.72/api/v1/login/";
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager=new SessionManager(this);

        edit_phone=findViewById(R.id.edit_phone);
        edit_pass=findViewById(R.id.edit_pass);
        but_login=findViewById(R.id.but_login);
        txt_signUp=findViewById(R.id.txt_signUp);
        intent=getIntent();
        userType=intent.getStringExtra("type");
        but_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String phone=edit_phone.getText().toString().trim();
               String pass=edit_pass.getText().toString().trim();
               login(phone,pass,userType);
            }
        });
       txt_signUp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i=new Intent(Login.this, Signup.class);
               i.putExtra("type",userType);
               startActivity(i);

           }
       });
    }
    private void login(final String phone, final String pass, final String type){
        if (phone.isEmpty()) {
            edit_phone.setError("Enter phone number");
            edit_phone.requestFocus();
        }
        if (pass.isEmpty()) {
            edit_pass.setError("enter password");
            edit_pass.requestFocus();
        }
        if (phone.isEmpty()||pass.isEmpty()){
            return;
        }

        StringRequest stringRequest =new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                          //  String success=jsonObject.getString("success");
                            if (response!=null){
                                SessionManager sessionManager =new SessionManager(Login.this);
                                String rid,rname,rphone,remail,raddress,rssn;
                                int rtotal_order;
                                double rate=0.0;
                                Intent intent=new Intent(Login.this,BidgeActivity.class);
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
                                    sessionManager.createSesion(rname,rphone,remail,rssn,type,rid,String.valueOf(rtotal_order),String.valueOf(rate));
                                }
                                startActivity(intent);
                            }else {
                                Toast.makeText(Login.this, "Filed!", Toast.LENGTH_SHORT).show();
                            }

                        }catch (JSONException e){
                                e.printStackTrace();
                            Toast.makeText(Login.this, "2Error!"+e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, "1Error!"+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("phone_number",phone);
                params.put("password",pass);
                return  params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
