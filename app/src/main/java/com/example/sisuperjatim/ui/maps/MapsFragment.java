package com.example.sisuperjatim.ui.maps;

import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.cocoahero.android.geojson.GeoJSON;
import com.cocoahero.android.geojson.GeoJSONObject;
import com.example.sisuperjatim.R;
import com.example.sisuperjatim.SessionManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;



import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    SearchView mSearchView;
    SessionManager sessionManager;
    Marker marker;



    private MapsViewModel dashboardViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = ViewModelProviders.of(this).get(MapsViewModel.class);
        mView = inflater.inflate(R.layout.fragment_maps, container, false);
        mMapView = (MapView) mView.findViewById(R.id.mapview);
        mMapView.onCreate(savedInstanceState);

        sessionManager = new SessionManager(getContext());
//        String latitude = getArguments().getString("lat", "a");
//        String longitude = getArguments().getString("lang", "a");
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

//        mMapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap mMap) {
//                mGoogleMap = mMap;
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

        mMapView = (MapView) mView.findViewById(R.id.mapview);
        if (mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        // For showing a move to my location button
        mGoogleMap.setMyLocationEnabled(true);
        HashMap<String, String> user = sessionManager.getUserMapData();
        String extraLatitude = user.get(sessionManager.LATITUDE);
        String extraLongitude = user.get(sessionManager.LONGITUDE);
        Double realLatitude = Double.parseDouble(extraLatitude);
        Double realLongitude = Double.parseDouble(extraLongitude);
        // For dropping a marker at a point on the Map
        Location location = new Location("a");
        location.setLatitude(realLatitude);
        location.setLongitude(realLongitude);
        LatLng sydney = new LatLng(location.getLatitude(),location.getLongitude());
        marker = mGoogleMap.addMarker(new MarkerOptions().position(sydney).title(sydney.toString()));
        mGoogleMap.addMarker(new MarkerOptions().position(sydney).title(sydney.toString()));

        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = CameraPosition.builder().target(sydney).zoom(14).bearing(0).tilt(0).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

//        mGoogleMap = googleMap;
//        googleMap  = EventBus.getDefault().getStickyEvent(GoogleMap.class);
//        CameraPosition Liberty = CameraPosition.builder().target(new LatLng(-7.9637127,112.6131008)).zoom(14).bearing(0).tilt(0).build();
//        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));
//
////        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
//        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
////        try{
//            GeoJsonLayer layer = new GeoJsonLayer(mGoogleMap, R.raw.administrasi, getContext());
//            GeoJsonPolygonStyle polygonStyle = layer.getDefaultPolygonStyle();
//            polygonStyle.setStrokeColor(Color.CYAN);
//            polygonStyle.setStrokeWidth(2);
//
//            Point point = new Point(38.889462878011365, -77.03525304794312);
//            layer.addFeature();
//            layer.addLayerToMap();
//
//
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//        catch(JSONException e){
//            e.printStackTrace();
//        }

//        String string; // A string containing GeoJSON
//
//        try {
//            GeoJSONObject geoJSONObject = GeoJSON.parse(string);
//        }
//        catch (JSONException e) {
//            e.printStackTrace();
//        }
//        googleMap.addMarker(new MarkerOptions().position(new LatLng(-7.9637127,112.6131008)).title("Warning").snippet("Percobaan"));
//        CameraPosition Liberty = CameraPosition.builder().target(new LatLng(-7.9637127,112.6131008)).zoom(14).bearing(0).tilt(0).build();
//        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));


    }
}