package com.example.sisuperjatim;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cocoahero.android.geojson.Feature;
import com.example.sisuperjatim.ui.maps.MapsFragment;
import com.example.sisuperjatim.ui.profile.ProfileFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.Point;
import com.google.maps.android.data.geojson.GeoJsonLayer;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.content.Context.LOCATION_SERVICE;

public class InputFragment extends Fragment implements OnMapReadyCallback{
//    protected void onCreate (Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_input);
//    }
//
//    private void setContentView(int fragment_input) {
//    }


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    SessionManager sessionManager;
    GoogleMap mGoogleMap;
    MapView mMapView, mapinput;
    View mView;
    LatLng titik;
    Marker marker;
    Location aLocation = new Location("a");
    private EditText name, edVarietas, edCatatan,edKab_kota, edKecamatan, edDesa;
    private  Spinner  koordinat, jenis_lahan, bahan_induk,
            penggunaan_lahan, relief, kondisi_lahan,
            drainase, reaksi_tanah, tanaman_utama, tekstur, kedalaman_olah, pola_tanaman,
            provitas, penggunaan_pupuk;
    private Button submit;
    private static String INPUT_URL = "http://bptpjatim.com/input.php";




    private OnFragmentInteractionListener mListener;

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.setMyLocationEnabled(true);
        aLocation = getLastKnownLocation();
        LatLng latLng = new LatLng(aLocation.getLatitude(),aLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                .title("Posisi Anda Sekarang");
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        marker = mGoogleMap.addMarker(markerOptions);


        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
        {
            @Override
            public void onMapClick(LatLng latLng) {
                if (marker != null){
                    marker.remove();
                }
                aLocation.setLatitude(latLng.latitude);
                aLocation.setLongitude(latLng.longitude);
                LatLng newLatlng = new LatLng(aLocation.getLatitude(), aLocation.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions().position(newLatlng).title(newLatlng.toString());
                marker = mGoogleMap.addMarker(markerOptions);
                Toast.makeText(getContext(),
                        "Lat : " + aLocation.getLatitude() + " , "
                                + "Long : " + aLocation.getLongitude(),
                        Toast.LENGTH_LONG).show();
            }
        });
//
//
//        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                latLng = titik;
//                CameraPosition Liberty = CameraPosition.builder().target(latLng).zoom(14).bearing(0).tilt(0).build();
//                mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));
//                Toast.makeText(getContext(),
//                        "Lat : " + latLng.latitude + " , "
//                                + "Long : " + latLng.longitude,
//                        Toast.LENGTH_LONG).show();
//
//
//            }
//
//        });



    }


    public InputFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mMapView =  mView.findViewById(R.id.mapinput);
        if (mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);


        }
//        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                Location location = new Location("a");
//                location.setLatitude(latLng.latitude);
//                location.setLongitude(latLng.longitude);
//
//                LatLng newLatlng = new LatLng(location.getLatitude(), location.getLongitude());
//                MarkerOptions markerOptions = new MarkerOptions().position(newLatlng).title(newLatlng.toString());
//                mGoogleMap.addMarker(markerOptions);
//            }
//        });


        name = view.findViewById(R.id.nama);
        edKab_kota = view.findViewById(R.id.kabupaten);
        edDesa = view.findViewById(R.id.desa);
        edKecamatan = view.findViewById(R.id.kecamatan);

        jenis_lahan = view.findViewById(R.id.jenis_lahan);
        bahan_induk = view.findViewById(R.id.bahan_induk);
        penggunaan_lahan = view.findViewById(R.id.penggunaan_lahan);
        relief = view.findViewById(R.id.relief);
        kondisi_lahan = view.findViewById(R.id.kondisi_lahan);
        drainase = view.findViewById(R.id.drainase);
        reaksi_tanah = view.findViewById(R.id.reaksi_tanah);
        tanaman_utama = view.findViewById(R.id.tanaman_utama);
        tekstur = view.findViewById(R.id.tekstur);
        kedalaman_olah = view.findViewById(R.id.kedalaman_olah);
        pola_tanaman = view.findViewById(R.id.pola_tanam);
        edVarietas = view.findViewById(R.id.varietas);
        edCatatan = view.findViewById(R.id.catatan);
        provitas = view.findViewById(R.id.provitas);
        penggunaan_pupuk = view.findViewById(R.id.penggunaan_pupuk);
        submit = view.findViewById(R.id.submit);

        sessionManager = new SessionManager(getContext());
        HashMap<String, String> user = sessionManager.getUserData();
        String extraNama = user.get(sessionManager.NAME);

        name.setText(extraNama);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mName = name.getText().toString().trim();
                String mVarietas = edVarietas.getText().toString().trim();
                String mKab_kot = edKab_kota.getText().toString().trim();
                String mKecamatan = edKecamatan.getText().toString().trim();
                String mDesa = edDesa.getText().toString().trim();
                if (!mName.isEmpty() &&  !mVarietas.isEmpty() && !mKab_kot.isEmpty() && !mKecamatan.isEmpty() && !mDesa.isEmpty()){
                    input();
                    Toast.makeText(getContext(), "Input Success!", Toast.LENGTH_SHORT);
                }
                if (mName.equals("")){
                    name.setError("Empty");
                }if (mVarietas.equals("")){
                    edVarietas.setError("Empty");
                }if (mKab_kot.equals("")){
                    edKab_kota.setError("Empty");
                }if (mKecamatan.equals("")){
                    edKecamatan.setError("Empty");
                }if (mDesa.equals("")){
                    edDesa.setError("Empty");
                }

            }
        });


    }

    private Location getLastKnownLocation() {
        Location l=null;
        LocationManager mLocationManager = (LocationManager)getContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
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

    private void input(){

//        TextView tDesa = (TextView)this.desa.getSelectedView();
//        TextView tKab_kota = (TextView)this.kab_kota.getSelectedView();
        TextView  tJenis_lahan = (TextView)this.jenis_lahan.getSelectedView();
        TextView tBahan_induk = (TextView)this.bahan_induk.getSelectedView();
        final double tKoordinatLat = aLocation.getLatitude();
        final double tKoordinatLang = aLocation.getLongitude();
        TextView tPenggunaan_lahan = (TextView)this.penggunaan_lahan.getSelectedView();
        TextView tRelief = (TextView)this.relief.getSelectedView();
        TextView tKondisi_lahan =  (TextView)this.kondisi_lahan.getSelectedView();
        TextView tDrainase = (TextView)this.drainase.getSelectedView();
        TextView tReaksi_tanah = (TextView)this.reaksi_tanah.getSelectedView();
        TextView tTanaman_utama = (TextView)this.tanaman_utama.getSelectedView();
        TextView tTekstur = (TextView)this.tekstur.getSelectedView();
        TextView tKedalaman_olah = (TextView)this.kedalaman_olah.getSelectedView();
        TextView tPola_tanam = (TextView)this.pola_tanaman.getSelectedView();
        TextView tProvitas = (TextView)this.provitas.getSelectedView();
        TextView tPenggunaan_pupuk = (TextView)this.penggunaan_pupuk.getSelectedView();
//        TextView tKecamatan = (TextView)this.kecamatan.getSelectedView();

        final String nama = name.getText().toString().trim();
        final String desa = edDesa.getText().toString().trim();
        final String kab_kota = edKab_kota.getText().toString().trim();
        final String jenis_lahan = tJenis_lahan.getText().toString().trim();
        final String bahan_induk = tBahan_induk.getText().toString().trim();
        final String penggunaan_lahan = tPenggunaan_lahan.getText().toString().trim();
        final String relief = tRelief.getText().toString().trim();
        final String kondisi_lahan = tKondisi_lahan.getText().toString().trim();
        final String drainase = tDrainase.getText().toString().trim();
        final String reaksi_tanah = tReaksi_tanah.getText().toString().trim();
        final String tanaman_utama = tTanaman_utama.getText().toString().trim();
        final String tekstur = tTekstur.getText().toString().trim();
        final String kedalaman_olah = tKedalaman_olah.getText().toString().trim();
        final String pola_tanam = tPola_tanam.getText().toString().trim();
        final String varietas = edVarietas.getText().toString().trim();
        final String provitas = tProvitas.getText().toString().trim();
        final String penggunaan_pupuk = tPenggunaan_pupuk.getText().toString().trim();
        final String kecamatan = edKecamatan.getText().toString().trim();
        final String koordinatLang = Double.toString(tKoordinatLang).trim();
        final String koordinatLat = Double.toString(tKoordinatLat).trim();
        final String catatan = edCatatan.getText().toString().trim();



        StringRequest stringRequest = new StringRequest(Request.Method.POST, INPUT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
//                            GeoJsonLayer layer = new GeoJsonLayer(mGoogleMap, R.raw.administrasi, getContext());
                            if (success.equals("1")){
//                                Point point = new Point(titik);
//                                Feature feature = new Feature(point);
//                                LatLng newLatlng = new LatLng(titik.latitude, titik.longitude);
//                                MarkerOptions markerOptions = new MarkerOptions().position(newLatlng).title(newLatlng.toString());
//                                Feature feature = new Feature();
//
//                                EventBus.getDefault().postSticky(mGoogleMap);
//                                Intent intent = new Intent(getContext(), MapsFragment.class);
//
//                                Intent intent = new Intent(getContext(), MapsFragment.class);
//                                intent.putExtra("lat", koordinatLat);
//                                intent.putExtra("lang", koordinatLang);
                                sessionManager.createSessionMap(tKoordinatLat, tKoordinatLang);
                                Toast.makeText(getContext(),"Success!",
                                        Toast.LENGTH_SHORT).show();
                                Intent home = new Intent(getContext(), MainActivity.class);
                                startActivity(home);


                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(getContext(),
                                    "Something's wrong :( \nDetails : \n" +
                                            e.toString(),
                                    Toast.LENGTH_SHORT).show();
                            submit.setVisibility(View.VISIBLE);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),
                                "Something's REALLY wrong :( \nDetails : \n" +
                                        error.toString(),
                                Toast.LENGTH_SHORT).show();
                        submit.setVisibility(View.VISIBLE);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama", nama);
                params.put("desa", desa);
                params.put("kab_kota", kab_kota);
                params.put("kecamatan", kecamatan);
                params.put("koordinat_lat", koordinatLat);
                params.put("koordinat_lang", koordinatLang);
                params.put("jenis_lahan", jenis_lahan);
                params.put("bahan_induk", bahan_induk);
                params.put("penggunaan_lahan", penggunaan_lahan);
                params.put("relief", relief);
                params.put("kondisi_lahan", kondisi_lahan);
                params.put("drainase", drainase);
                params.put("reaksi_tanah", reaksi_tanah);
                params.put("tanaman_utama", tanaman_utama);
                params.put("tekstur", tekstur);
                params.put("kedalaman_olah", kedalaman_olah);
                params.put("pola_tanam", pola_tanam);
                params.put("varietas", varietas);
                params.put("provitas", provitas);
                params.put("penggunaan_pupuk", penggunaan_pupuk);
                params.put("catatan",catatan);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_input, container, false);

        return mView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
