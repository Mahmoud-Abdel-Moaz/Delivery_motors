package com.example.deliverymotors.Client;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.deliverymotors.Model.CaptainLocation;
import com.example.deliverymotors.Model.ClientLocation;
import com.example.deliverymotors.R;
import com.example.deliverymotors.SessionManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
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
public class ClientMapFragment extends Fragment implements OnMapReadyCallback , GoogleMap.OnMarkerClickListener{

    private GoogleMap mMap;
    SessionManager sessionManager ;
    DatabaseReference reference;
    DatabaseReference Creference;
    HashMap<String,String> userhash;
    int con=1;
    List<Marker> markerList = new ArrayList<Marker>();
   List<CaptainLocation> captainLocations=new ArrayList<CaptainLocation>();

    /////////////////////////////////location Stafe///////////////////////
    LocationManager locationManager;
    LocationListener locationListener;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0, locationListener);
            }
        }
    }
    /////////////////////////////////

    public ClientMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_captains_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map_ClientRequest);
        mapFragment.getMapAsync(this);
        sessionManager=new SessionManager(getActivity());
        userhash=sessionManager.getUserDetail();

        return view;
    }


    private BitmapDescriptor ConvertFromAssetsToImage(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_bicycle_black);
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
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.setOnMarkerClickListener(this);
        mMap = googleMap;
        googleMap.setOnMarkerClickListener(this);
        reference= FirebaseDatabase.getInstance().getReference("Client_locations").child(userhash.get(SessionManager.PHONE));
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mMap.clear();
               // googleMap.clear();
                markerList.clear();
                double longitude,latitude;
             //   Toast.makeText(getActivity(), String.valueOf( dataSnapshot.child("latitude").getValue()), Toast.LENGTH_SHORT).show();
                longitude=Double.parseDouble( String.valueOf(dataSnapshot.child("longitude").getValue()));

                latitude=Double.parseDouble( String.valueOf(dataSnapshot.child("latitude").getValue()));
                LatLng client=new LatLng(longitude,latitude);
                Marker client_marker = mMap.addMarker(new MarkerOptions().position(client).title("You").icon(ConvertFromAssetsToImage2(getActivity(), R.drawable.ic_person_map)));
                markerList.add(client_marker);
                if (con==1){
                     mMap.moveCamera(CameraUpdateFactory.newLatLng(client));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                con++;
                }


               Creference= FirebaseDatabase.getInstance().getReference("Captain_locations");
               Creference.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       captainLocations.clear();
                       for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                           CaptainLocation captainLocation=new CaptainLocation();
                           captainLocation.setCaptienNumber((String) snapshot.child("captienNumber").getValue());
                           captainLocation.setName((String) snapshot.child("name").getValue());
                           captainLocation.setImage((String) snapshot.child("image").getValue());
                           captainLocation.setState((Boolean) snapshot.child("state").getValue());
                           captainLocation.setLongitude((Double.parseDouble(String.valueOf(snapshot.child("longitude").getValue()))));
                           captainLocation.setLatitude((Double.parseDouble(String.valueOf(snapshot.child("latitude").getValue()))));
                           captainLocation.setRate((Double.parseDouble(String.valueOf(snapshot.child("rate").getValue()))));
                           captainLocations.add(captainLocation);
                           LatLng captien=new LatLng(captainLocation.getLongitude(),captainLocation.getLatitude());
                           Marker captain_marker = mMap.addMarker(new MarkerOptions().position(captien).title(captainLocation.getName()).icon(ConvertFromAssetsToImage(getActivity(), R.drawable.ic_bicycle_black)));
                           markerList.add(captain_marker);
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

      /*  LatLng userlocation = new LatLng(27.174635417883025, 31.19179056120029);
        mMap.addMarker(new MarkerOptions().position(userlocation).title("Your Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userlocation));*/
    /*  mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
          @Override
          public void onInfoWindowClick(Marker marker) {

             // String id = marker.getId().toString();
            //  id = id.substring(1);

             int markerId=-1;

              String str = marker.getId();
              for(int i=0; i<markerList.size(); i++) {
                  markerId = i;
                  Marker m = markerList.get(i);
                  if(m.getId().equals(marker.getId()))
                      break;
              }

              int ind =markerId-1;
              Toast.makeText(getActivity(), ind+" "+markerId, Toast.LENGTH_SHORT).show();
              if (ind >0) {
                  String CaoName=captainLocations.get(ind).getName();
                  String CaoNamner=captainLocations.get(ind).getCaptienNumber();
                  String CaoImage=captainLocations.get(ind).getImage();
                  double CaoRate=captainLocations.get(ind).getRate();
                  Intent intent=new Intent(getActivity(),Pop_CaptainActivity.class);
                  intent.putExtra("CaoName",CaoName);
                  intent.putExtra("CaoNamner",CaoNamner);
                  intent.putExtra("CaoImage",CaoImage);
                  intent.putExtra("CaoRate",String.valueOf(CaoRate));
                  startActivity(intent);
              }


          }
      });
*/


        //////////////////////////////Location Stafe/////////////////////
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0, locationListener);
        }
        /////////////////////////////////////



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

        int ind =markerId-1;

        if (ind > -1) {
            String CaoName=captainLocations.get(ind).getName();
            String CaoNamner=captainLocations.get(ind).getCaptienNumber();
            String CaoImage=captainLocations.get(ind).getImage();
            double CaoRate=captainLocations.get(ind).getRate();
            Intent intent=new Intent(getActivity(),Pop_CaptainActivity.class);
            intent.putExtra("CaoName",CaoName);
            intent.putExtra("CaoNamner",CaoNamner);
            intent.putExtra("CaoImage",CaoImage);
            intent.putExtra("CaoRate",String.valueOf(CaoRate));
            startActivity(intent);
        }

        return false;
    }
}
