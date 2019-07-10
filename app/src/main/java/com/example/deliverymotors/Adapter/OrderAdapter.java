package com.example.deliverymotors.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deliverymotors.Captain.CaptainReceiptOrderActivty;
import com.example.deliverymotors.Client.ClientReceiptOrderActivity;
import com.example.deliverymotors.Model.Captain;
import com.example.deliverymotors.Model.Order;
import com.example.deliverymotors.OrderDetailsActivity;
import com.example.deliverymotors.OrdersActivity;
import com.example.deliverymotors.R;
import com.example.deliverymotors.SessionManager;
import com.example.deliverymotors.StartActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
       List<Order> data =new ArrayList<>();
        Context con;
        SessionManager sessionManager;
    HashMap<String,String> user;
    boolean done;

    public OrderAdapter(List<Order> data, Context con) {
        this.data = data;
        this.con = con;
        sessionManager=new SessionManager(con);
        user=sessionManager.getUserDetail();

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(con).inflate(R.layout.order_item,parent,false);
        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Order order=data.get(i);
        done=true;
       DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Send_Orders");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String capN=(String)snapshot.child("captainNumber").getValue();
                    String cliN=(String)snapshot.child("clientNumber").getValue();
                    if (capN.equals(order.getCaptainName())&&cliN.equals(order.getCaptainNumber())){
                        done=false;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        viewHolder.txt_date.setText(order.getStartDate());
        viewHolder.txt_from.setText(order.getFrom());
        viewHolder.txt_price.setText("9El");
        if (done){
            viewHolder.txt_isCompleted.setText("Completed");
            viewHolder.txt_isCompleted.setTextColor(con.getResources().getColor(R.color.LightGreen));
        }
        else if(!done){
            viewHolder.txt_isCompleted.setText("In progress");
            viewHolder.txt_isCompleted.setTextColor(con.getResources().getColor(R.color.Orange));
        }
        viewHolder.txt_date.setText(order.getStartDate());
        viewHolder.txt_to.setText(order.getTo());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!done){
                    if (user.get(SessionManager.USERTYPE).equals("client")){
                        Intent i=new Intent(con, ClientReceiptOrderActivity.class);
                        i.putExtra("price",order.getPrice());
                        i.putExtra("capNumber",order.getCaptainNumber());
                        i.putExtra("cliNumber",order.getClientNumber());
                        con.startActivity(i);

                    }else if(user.get(SessionManager.USERTYPE).equals("captain")){
                        Intent i=new Intent(con, CaptainReceiptOrderActivty.class);
                        i.putExtra("capNumber",order.getCaptainNumber());
                        i.putExtra("cliNumber",order.getClientNumber());
                        con.startActivity(i);

                    }
                }else if (order.isCompleted()){
                    Intent i=new Intent(con, OrderDetailsActivity.class);
                    i.putExtra("from",order.getFrom());
                    i.putExtra("to",order.getTo());
                    i.putExtra("startDate",order.getStartDate());
                    i.putExtra("endDate",order.getEndDate());
                    i.putExtra("price",order.getPrice());
                    i.putExtra("order",order.getOrderDetail());
                    i.putExtra("from",order.getFrom());

                    if (user.get(SessionManager.USERTYPE).equals("client")){
                        i.putExtra("name",order.getClientName());

                    }else if(user.get(SessionManager.USERTYPE).equals("captain")){
                        i.putExtra("name",order.getCaptainName());
                    }
                    con.startActivity(i);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_price,txt_date,txt_from,txt_to,txt_isCompleted;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_price=itemView.findViewById(R.id.txt_price);
            txt_date=itemView.findViewById(R.id.txt_date);
            txt_from=itemView.findViewById(R.id.txt_from);
            txt_to=itemView.findViewById(R.id.txt_to);
            txt_isCompleted=itemView.findViewById(R.id.txt_isCompleted);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

}
