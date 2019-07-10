package com.example.deliverymotors.Captain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliverymotors.BidgeActivity;
import com.example.deliverymotors.Model.CaptainLocation;
import com.example.deliverymotors.Model.ClientLocation;
import com.example.deliverymotors.R;
import com.example.deliverymotors.SessionManager;
import com.example.deliverymotors.Verificationcode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Pop_ClientActivity extends AppCompatActivity {
    CircleImageView client_img;
    TextView txt_name,txt_order,txt_from,txt_to;
    Button but_accept,but_reject;
    String CaptainId;
    DatabaseReference reference;
    SessionManager sessionManager;
    HashMap<String,String> user;
    private static String URL_POSTORDER="http://192.168.1.11/api/v1/orders/";
    Intent intent;
    String orderDetail;
    String clientNumber;
    String captainNumber;
    String clientName;
    String to;
    String from;
    String ClientImage;
    String OrderId;
    String clientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop__client);
        client_img=findViewById(R.id.client_img);
        txt_name=findViewById(R.id.txt_name);
        txt_order=findViewById(R.id.txt_order);
        txt_from=findViewById(R.id.txt_from);
        txt_to=findViewById(R.id.txt_to);
        but_accept=findViewById(R.id.but_accept);
        but_reject=findViewById(R.id.but_reject);
        intent=getIntent();
        orderDetail=intent.getStringExtra("orderDetail");
        clientNumber=intent.getStringExtra("clientNumber");
        captainNumber=intent.getStringExtra("captainNumber");
        clientName=intent.getStringExtra("clientName");
        to=intent.getStringExtra("to");
        from=intent.getStringExtra("from");
        ClientImage=intent.getStringExtra("ClientImage");
        OrderId=intent.getStringExtra("OrderId");
        clientId=intent.getStringExtra("clientId");
        URL_POSTORDER=URL_POSTORDER+clientId+"/";
        txt_name.setText(clientName);
        txt_order.setText(orderDetail);
        txt_from.setText(from);
        txt_to.setText(to);
        sessionManager=new SessionManager(this);
        user=sessionManager.getUserDetail();
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*.7),(int)(height*.8));

        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x=0;
        params.y=-20;
        but_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference()
                        .child("Send_Orders").child(OrderId);
                reference.removeValue();
                finish();
            }
        });
        but_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SandOrder();
                finish();
            }
        });
    }

    public void SandOrder(){
        intent=getIntent();
        orderDetail=intent.getStringExtra("orderDetail");
        clientNumber=intent.getStringExtra("clientNumber");
        captainNumber=intent.getStringExtra("captainNumber");
        clientName=intent.getStringExtra("clientName");
        to=intent.getStringExtra("to");
        from=intent.getStringExtra("from");
        ClientImage=intent.getStringExtra("ClientImage");
        OrderId=intent.getStringExtra("OrderId");
        CaptainId=user.get(SessionManager.ID);
        clientId=intent.getStringExtra("clientId");
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_POSTORDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response!=null){
                               JSONObject jsonObject=new JSONObject(response);
                            }else {
                                Toast.makeText(Pop_ClientActivity.this, "Filed!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Pop_ClientActivity.this, "Register Error! "+e.toString(), Toast.LENGTH_SHORT).show();
                            // loading.setVisibility(View.GONE);

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(Pop_ClientActivity.this, "Register Error! "+error.toString(), Toast.LENGTH_SHORT).show();
                        //loading.setVisibility(View.GONE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                String strDate = dateFormat.format(date);
                Map<String,String> params=new HashMap<>();
                params.put("start_time",strDate);
                params.put("details",orderDetail);
                params.put("location_from",from);
                params.put("location_to",to);
                params.put("client",clientId);
                params.put("driver",CaptainId);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
