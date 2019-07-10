package com.example.deliverymotors.Captain;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deliverymotors.Model.ClientLocation;
import com.example.deliverymotors.Model.ReaceverOrder;
import com.example.deliverymotors.Model.SendOrder;
import com.example.deliverymotors.R;
import com.example.deliverymotors.SessionManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CaptainMapFragment extends Fragment implements OnMapReadyCallback , GoogleMap.OnMarkerClickListener{
    private GoogleMap mMap;
    SessionManager sessionManager ;
    DatabaseReference Creference;
    DatabaseReference reference;
    DatabaseReference Ordersreference;
    HashMap<String,String> userhash;
    List<Marker> markerList = new ArrayList<Marker>();
    List<ReaceverOrder> reaceverOrders=new ArrayList<ReaceverOrder>();
    int con=1;
    public CaptainMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_captain_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map_capain);
        mapFragment.getMapAsync(this);
        sessionManager=new SessionManager(getActivity());
        userhash=sessionManager.getUserDetail();
        return v;
    }
    private BitmapDescriptor ConvertFromAssetsToImage(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_place_black);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(60, 60, vectorDrawable.getIntrinsicWidth() + 60, vectorDrawable.getIntrinsicHeight() + 60);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private BitmapDescriptor ConvertFromAssetsToImage2(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_person_map);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(60, 60, vectorDrawable.getIntrinsicWidth() + 60, vectorDrawable.getIntrinsicHeight() + 60);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            googleMap.setOnMarkerClickListener(this);
            mMap = googleMap;
            googleMap.setOnMarkerClickListener(this);
            reference= FirebaseDatabase.getInstance().getReference("Captain_locations").child(userhash.get(SessionManager.PHONE));
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mMap.clear();
                   // googleMap.clear();
                    markerList.clear();
                    double longitude,latitude;
                    longitude=Double.parseDouble( String.valueOf(dataSnapshot.child("longitude").getValue()));
                    latitude=Double.parseDouble( String.valueOf(dataSnapshot.child("latitude").getValue()));
                    LatLng captain=new LatLng(longitude,latitude);

                    Marker captain_marker = mMap.addMarker(new MarkerOptions().position(captain).title("You").icon(ConvertFromAssetsToImage2(getActivity(), R.drawable.ic_person_map)));
                    markerList.add(captain_marker);
                    if (con==1){
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(captain));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                        con++;
                    }
                    Ordersreference= FirebaseDatabase.getInstance().getReference("Send_Orders");
                    Ordersreference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            reaceverOrders.clear();
                            for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                                final String orderDetail=(String) snapshot.child("orderDetail").getValue();
                                final String clientNumber=(String) snapshot.child("clientNumber").getValue();
                                final String captainNumber=(String) snapshot.child("captainNumber").getValue();
                                final String clientName=(String) snapshot.child("clientName").getValue();
                                final String to=(String) snapshot.child("to").getValue();
                                final String from=(String) snapshot.child("from").getValue();
                                final String clientId=(String) snapshot.child("clientId").getValue();
                                final String orderId=(String)snapshot.getKey();
                                Creference= FirebaseDatabase.getInstance().getReference("Client_locations").child(clientNumber);
                                Creference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        double longitude,latitude;
                                        longitude=Double.parseDouble( String.valueOf(dataSnapshot.child("longitude").getValue()));
                                        latitude=Double.parseDouble( String.valueOf(dataSnapshot.child("latitude").getValue()));
                                        String ClientImage=(String)dataSnapshot.child("image").getValue();
                                        LatLng client=new LatLng(longitude,latitude);
                                        Marker client_marker = mMap.addMarker(new MarkerOptions().position(client).title(clientName).icon(ConvertFromAssetsToImage(getActivity(), R.drawable.ic_place_black)));
                                        markerList.add(client_marker);
                                        ReaceverOrder reaceverOrder=new ReaceverOrder(orderDetail,clientNumber,captainNumber,clientName,to,from,ClientImage,orderId,clientId);
                                        reaceverOrders.add(reaceverOrder);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
          /*  mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    int markerId = -1;

                    String str = marker.getId();
                    for(int i=0; i<markerList.size(); i++) {
                        markerId = i;
                        Marker m = markerList.get(i);
                        if(m.getId().equals(marker.getId()))
                            break;
                    }
                    final int ind =markerId-1;
                    if (ind >0) {
                        String orderDetail=reaceverOrders.get(ind).getOrderDetail();
                        String clientNumber=reaceverOrders.get(ind).getClientNumber();
                        String captainNumber=reaceverOrders.get(ind).getCaptainNumber();
                        String clientName=reaceverOrders.get(ind).getClientName();
                        String to=reaceverOrders.get(ind).getTo();
                        String from=reaceverOrders.get(ind).getFrom();
                        String ClientImage=reaceverOrders.get(ind).getClientImage();
                        String orderId=reaceverOrders.get(ind).getOrderId();
                        Intent i=new Intent(getActivity(),Pop_ClientActivity.class);
                        i.putExtra("orderDetail",orderDetail);
                        i.putExtra("clientNumber",clientNumber);
                        i.putExtra("captainNumber",captainNumber);
                        i.putExtra("clientName",clientName);
                        i.putExtra("to",to);
                        i.putExtra("from",from);
                        i.putExtra("ClientImage",ClientImage);
                        i.putExtra("orderId",orderId);
                        startActivity(i);
                    }
                }
            });*/

        }

    @Override
    public boolean onMarkerClick(Marker marker) {
        int markerId = -1;

        String str = marker.getId();
        for(int i=0; i<markerList.size(); i++) {
            markerId = i;
            Marker m = markerList.get(i);
            if(m.getId().equals(marker.getId()))
                break;
        }
        final int ind =markerId-1;
        if (ind >-1) {
            String orderDetail=reaceverOrders.get(ind).getOrderDetail();
            String clientNumber=reaceverOrders.get(ind).getClientNumber();
            String captainNumber=reaceverOrders.get(ind).getCaptainNumber();
            String clientName=reaceverOrders.get(ind).getClientName();
            String to=reaceverOrders.get(ind).getTo();
            String from=reaceverOrders.get(ind).getFrom();
            String ClientImage=reaceverOrders.get(ind).getClientImage();
            String orderId=reaceverOrders.get(ind).getOrderId();
            String clientId=reaceverOrders.get(ind).getClientId();
            Intent i=new Intent(getActivity(),Pop_ClientActivity.class);
            i.putExtra("orderDetail",orderDetail);
            i.putExtra("clientNumber",clientNumber);
            i.putExtra("captainNumber",captainNumber);
            i.putExtra("clientName",clientName);
            i.putExtra("to",to);
            i.putExtra("from",from);
            i.putExtra("ClientImage",ClientImage);
            i.putExtra("orderId",orderId);
            i.putExtra("clientId",clientId);
            startActivity(i);
        }
        return false;
    }
}
