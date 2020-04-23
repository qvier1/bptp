package com.example.sisuperjatim.ui.maps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.sisuperjatim.ProfileActivity;
import com.example.sisuperjatim.R;
import com.example.sisuperjatim.SessionManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.LOCATION_SERVICE;

public class MapsFragment extends Fragment implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    SearchView mSearchView;
    SessionManager sessionManager;
    Marker marker;
    Button btnInput;
    LocationManager locationManager ;
    String nama;
    MapsFragment mapsFragment;

    Location currentlocation;
    FusedLocationProviderClient fusedLocationProviderClient;


    protected GoogleApiClient mGoogleApiClient;


    public static MapsFragment newInstance(){
        return new MapsFragment();
    }



    private MapsViewModel dashboardViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = ViewModelProviders.of(this).get(MapsViewModel.class);
        mView = inflater.inflate(R.layout.fragment_maps, container, false);
        mMapView = (MapView) mView.findViewById(R.id.mapview);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        mMapView.getMapAsync(this);

        mMapView.onCreate(savedInstanceState);
//
//        Criteria criteria = new Criteria();
////        String latitude = getArguments().getString("lat", "a");
////        String longitude = getArguments().getString("lang", "a");
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


//        mMapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap mMap) {
//
//
//                mGoogleMap = mMap;
//                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//
//                try {
//
//                    GeoJsonLayer layer = new GeoJsonLayer(mMap, R.raw.administrasi, getContext());
//                    GeoJsonPolygonStyle polygonStyle = layer.getDefaultPolygonStyle();
//                    polygonStyle.setStrokeColor(Color.CYAN);
//                    polygonStyle.setStrokeWidth(2);
//                    layer.addLayerToMap();
//
//                } catch (Exception e) {
//
//                }
//
//
//                // For showing a move to my location button
//                mGoogleMap.setMyLocationEnabled(true);
//                sessionManager = new SessionManager(getContext());
//                HashMap<String, String> user = sessionManager.getUserMapData();
//                String extraLatitude = user.get(sessionManager.LATITUDE);
//                String extraLongitude = user.get(sessionManager.LONGITUDE);
//                Double realLatitude = Double.parseDouble(extraLatitude);
//                Double realLongitude = Double.parseDouble(extraLongitude);
//                // For dropping a marker at a point on the Map
//                LatLng sydney = new LatLng(realLatitude,realLongitude);
//                mGoogleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
//
//                // For zooming automatically to the location of the marker
//                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
//                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//            }
//        });

        return mView;
    }


//    private Location fetcLastLocation() {
//
//        Task<Location> task = fusedLocationProviderClient.getLastLocation();
//        task.addOnSuccessListener(new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                if (location != null){
//
//                    currentlocation = location;
//
//                    Toast.makeText(getContext(),currentlocation.getLatitude()
//                            + ""+currentlocation.getLongitude(),Toast.LENGTH_SHORT).show();
//                    SupportMapFragment supportMapFragment = (SupportMapFragment)
//                            getFragmentManager().findFragmentById(R.id.mapview);
//                    supportMapFragment.getMapAsync(MapsFragment.this);
//                }
//            }
//        });
//        return currentlocation;
//    }


    private Location getLastKnownLocation() {
        Location l=null;
        LocationManager mLocationManager = (LocationManager)getContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if(ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
                l = mLocationManager.getLastKnownLocation(provider);
            }
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;
    }



    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }



    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnInput = view.findViewById(R.id.inputButton);

        mMapView = (MapView) mView.findViewById(R.id.mapview);
        if (mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }



    }



    @Override
    public void onMapReady(final GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        sessionManager = new SessionManager(getContext());
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        // For showing a move to my location button
        mGoogleMap.setMyLocationEnabled(true);
        Location location = getLastKnownLocation();
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());

        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
//        mGoogleMap.addMarker(markerOptions);
        showAllInputByCurrentUser();


        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        JSONObject jsonObject = new
             try {

                GeoJsonLayer layer = new GeoJsonLayer(mGoogleMap, R.raw.administrasi, getContext());
                GeoJsonPolygonStyle polygonStyle = layer.getDefaultPolygonStyle();
                polygonStyle.setStrokeColor(Color.CYAN);
                polygonStyle.setStrokeWidth(2);
                layer.addLayerToMap();




                } catch (Exception e) {

                }




        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
//                Fragment mFrag = new InputFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.nav_host_fragment, mFrag);
////                transaction.addToBackStack("home");
////                Fragment previousInstance = getFragmentManager().findFragmentByTag("home");
////                if (previousInstance != null)
////                    transaction.remove(previousInstance);
//                transaction.commit();

            }
        });


    }

    private void showAllInputByCurrentUser() {
        String URL_SELECT_MARKERS = "http://bptpjatim.com/markers.php";
        HashMap<String, String> user = sessionManager.getUserData();
        nama = user.get(sessionManager.NAME);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_SELECT_MARKERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject hasil = jsonArray.getJSONObject(i);
                                Double lat = hasil.getDouble("latitude");
                                Double lang = hasil.getDouble("langitude");
                                LatLng latLng = new LatLng(lat,lang);
                                MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                                        .title(lat.toString() + " , " + lang.toString());
                                mGoogleMap.addMarker(markerOptions);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                       Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama",nama);



                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);





    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
        mGoogleMap.animateCamera(cameraUpdate);
        locationManager.removeUpdates(this);
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}