package com.example.deliverymotors;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.deliverymotors.Client.MainClientActivity;

import java.util.HashMap;
import java.util.NavigableMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE=0;
    private static final String PREF_NAME="LOGIN";
    private static final String LOGIN="IS_LOGIN";
    public static final String NAME ="NAME";
    public static final String PHONE="PHONE";
    public static final String EMAIL ="EMAIL";
    public static final String SSN="SSN";
    public static final String ADDRESS ="ADDRESS";
    public static final String USERTYPE="USERTYPE";
    public static final String ID="ID";
    public static final String TOTAL_ORDERS="TOTAL_ORDERS";
    public static final String RATE="RATE";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor=sharedPreferences.edit();
    }
    public void createSesion(String name,String phone,String email,String ssn,String usertype,String id,String total_orders,String rate){
        editor.putBoolean(LOGIN,true);
        editor.putString(NAME,name);
        editor.putString(PHONE,phone);
        editor.putString(EMAIL,email);
        editor.putString(SSN,ssn);
        //editor.putString(ADDRESS,address);
        editor.putString(USERTYPE,usertype);
        editor.putString(ID,id);
        editor.putString(TOTAL_ORDERS,total_orders);
        editor.putString(RATE,rate);
        editor.apply();
    }
    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN,false);
    }

    public void checkLogin(){
        if (!this.isLoggin()){
            Intent i=new Intent(context,StartActivity.class);
            context.startActivity(i);
            ((BidgeActivity)context).finish();
        }
    }
    public HashMap<String,String> getUserDetail(){
        HashMap<String,String> user=new HashMap<>();
        user.put(NAME,sharedPreferences.getString(NAME,null));
        user.put(PHONE,sharedPreferences.getString(PHONE,null));
        user.put(EMAIL,sharedPreferences.getString(EMAIL,null));
        user.put(SSN,sharedPreferences.getString(SSN,null));
       // user.put(ADDRESS,sharedPreferences.getString(ADDRESS,null));
        user.put(USERTYPE,sharedPreferences.getString(USERTYPE,null));

        user.put(ID,sharedPreferences.getString(ID,null));
        user.put(TOTAL_ORDERS,sharedPreferences.getString(TOTAL_ORDERS,null));
        user.put(RATE,sharedPreferences.getString(RATE,null));

        return user;
    }
    public void logout(){
        editor.clear();
        editor.commit();
      /*  Intent i=new Intent(context,StartActivity.class);
        context.startActivity(i);
        ((BidgeActivity)context).finish();*/
    }
}
