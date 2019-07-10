package com.example.deliverymotors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliverymotors.Adapter.OrderAdapter;
import com.example.deliverymotors.Client.Profile;
import com.example.deliverymotors.Model.CaptainLocation;
import com.example.deliverymotors.Model.ClientLocation;
import com.example.deliverymotors.Model.Order;
import com.example.deliverymotors.Model.Volly;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdersActivity extends AppCompatActivity {
    private static String URL_GETORDERS="http://192.168.43.72/api/v1/orders/";
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager ;
    SessionManager sessionManager;
    HashMap<String,String> user;
    String type,Id;
    private OrderAdapter orderAdapter;
    private List<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Orders");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView=findViewById(R.id.recycle_orders);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orders=new ArrayList<Order>();

        sessionManager=new SessionManager(this);
        user=sessionManager.getUserDetail();
        Id=user.get(SessionManager.ID);
        type=user.get(SessionManager.USERTYPE);
        if (type.equals("client")){
            getClient();
        }else  if (type.equals("captain")){
            getCaptain();
        }

        orderAdapter = new OrderAdapter(orders,this);
        recyclerView.setAdapter(orderAdapter);

    }
    private void getCaptain(){

        StringRequest stringRequest=new StringRequest(Request.Method.GET,URL_GETORDERS ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response!=null){
                               JSONArray jsonArray=new JSONArray(response);
                                for(int i=0;i<jsonArray.length();i++) {
                                    JSONObject esponse=jsonArray.getJSONObject(i);
                                    String orderDetail;
                                    String clientNumber;
                                    String captainNumber;
                                    String clientName;
                                    String to;
                                    String from;
                                    String startDate;
                                    String captainName;
                                    String captainId;
                                    orderDetail = esponse.getString("details");
                                    startDate = esponse.getString("start_time");
                                    to = esponse.getString("location_to");
                                    from =esponse.getString("location_from");
                                    JSONObject client =esponse.getJSONObject("client");
                                    clientName = client.getString("name");
                                    clientNumber = client.getString("phone_number");
                                    JSONObject captain =esponse.getJSONObject("driver");
                                    captainNumber = captain.getString("phone_number");
                                    captainName = captain.getString("name");
                                    captainId = captain.getString("id");
                                    Date date = Calendar.getInstance().getTime();
                                    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                                    String endDate = dateFormat.format(date);
                                    Order order = new Order(captainName, clientName, captainNumber, clientNumber, orderDetail, from, to, startDate, endDate, "90LE", true);
                                    if (Id.equals(captainId)) {
                                        orders.add(order);
                                    }

                                }

                            }else {
                                Toast.makeText(OrdersActivity.this, "Filed!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(OrdersActivity.this, "Register Error! "+e.toString(), Toast.LENGTH_SHORT).show();
                            // loading.setVisibility(View.GONE);

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(OrdersActivity.this, "Register Error! "+error.toString(), Toast.LENGTH_SHORT).show();
                        //loading.setVisibility(View.GONE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void getClient(){

        StringRequest stringRequest=new StringRequest(Request.Method.GET,URL_GETORDERS ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response!=null){
                                JSONArray jsonArray=new JSONArray(response);
                                for(int i=0;i<jsonArray.length();i++) {
                                    JSONObject esponse=jsonArray.getJSONObject(i);
                                    String orderDetail;
                                    String clientNumber;
                                    String captainNumber;
                                    String clientName;
                                    String to;
                                    String from;
                                    String startDate;
                                    String captainName;
                                    String calientId;

                                    orderDetail = esponse.getString("details");
                                    startDate = esponse.getString("start_time");
                                    to = esponse.getString("location_to");
                                    from =esponse.getString("location_from");
                                    JSONObject client =esponse.getJSONObject("client");
                                    clientName = client.getString("name");
                                    clientNumber = client.getString("phone_number");
                                    JSONObject captain =esponse.getJSONObject("driver");

                                    captainNumber = captain.getString("phone_number");
                                    captainName = captain.getString("name");
                                    calientId = client.getString("id");
                                    Date date = Calendar.getInstance().getTime();
                                    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                                    String endDate = dateFormat.format(date);
                                    Order order = new Order(captainName, clientName, captainNumber, clientNumber, orderDetail, from, to, startDate, endDate, "90LE", true);
                                    if (Id.equals(calientId)) {
                                        orders.add(order);
                                    }

                                }

                            }else {
                                Toast.makeText(OrdersActivity.this, "Filed!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(OrdersActivity.this, "Register Error! "+e.toString(), Toast.LENGTH_SHORT).show();
                            // loading.setVisibility(View.GONE);

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(OrdersActivity.this, "Register Error! "+error.toString(), Toast.LENGTH_SHORT).show();
                        //loading.setVisibility(View.GONE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
