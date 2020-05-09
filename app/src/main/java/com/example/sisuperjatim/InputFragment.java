package com.example.sisuperjatim;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
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
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
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
    private String extraNama, formattedDate = "";
    private EditText name,edCatatan, lereng,organikMH, organikMK1,organikMK2, ureaMH, ureaMK1, ureaMK2, kclMH, kclMK1, kclMK2,
            ponskaMH, ponskaMK1, ponskaMK2, sp36MH, sp36MK1, sp36MK2, zaMH, zaMK1, zaMK2,provitasMH,provitasMK1,provitasMK2;
    private  Spinner bahan_induk,
            penggunaan_lahan, relief,
            drainase, reaksi_tanah, tanaman_utama,  kedalaman_olah, jenis_lahan, pola_tanamanMH,pola_tanamanMK1,
            pola_tanamanMK2,
             penggunaan_pupuk,edKecamatan,edKab_kota, edDesa,
            varietasMH, varietasMK1, varietasMK2;
    private DatePicker tanggal;
    private ImageView image;
    private Button submit;
    private static String INPUT_URL = "http://bptpjatim.com/input.php";
    public static final int GET_FROM_GALLERY = 3;
    private Uri filepath;
    private Bitmap bitmap;
    private GridLayout gridLayout;




    private OnFragmentInteractionListener mListener;

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
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



        name = view.findViewById(R.id.edInitial);
        sessionManager = new SessionManager(getContext());
        HashMap<String, String> user = sessionManager.getUserData();
        extraNama = user.get(sessionManager.NAME);

//        edTanggal.setText(formattedDate);
        tanggal = view.findViewById(R.id.tanggal);
        edKab_kota = view.findViewById(R.id.kabupaten);
        edDesa = view.findViewById(R.id.desa);
        edKecamatan = view.findViewById(R.id.kecamatan);
        image = view.findViewById(R.id.foto);
        lereng = view.findViewById(R.id.lereng);
        jenis_lahan = view.findViewById(R.id.jenis_lahan);
        bahan_induk = view.findViewById(R.id.bahan_induk);
        penggunaan_lahan = view.findViewById(R.id.penggunaan_lahan);
        relief = view.findViewById(R.id.relief);
        drainase = view.findViewById(R.id.drainase);
        reaksi_tanah = view.findViewById(R.id.reaksi_tanah);
        tanaman_utama = view.findViewById(R.id.tanaman_utama);
        kedalaman_olah = view.findViewById(R.id.kedalaman_olah);
        pola_tanamanMH = view.findViewById(R.id.polatanamMH);
        pola_tanamanMK1 = view.findViewById(R.id.polatanamMK1);
        pola_tanamanMK2 = view.findViewById(R.id.polatanamMK2);
        varietasMH = view.findViewById(R.id.varietasMH);
        varietasMK1 = view.findViewById(R.id.varietasMK1);
        varietasMK2 = view.findViewById(R.id.varietasMK2);
        edCatatan = view.findViewById(R.id.catatan);
        provitasMH = view.findViewById(R.id.provitasMH);
        provitasMK1 = view.findViewById(R.id.provitasMK1);
        provitasMK2 = view.findViewById(R.id.provitasMK2);
        organikMH = view.findViewById(R.id.organikMH);
        organikMK1 = view.findViewById(R.id.organikMK1);
        organikMK2 = view.findViewById(R.id.organikMK2);
        ureaMH = view.findViewById(R.id.ureaMH);
        ureaMK1 = view.findViewById(R.id.ureaMK1);
        ureaMK2 = view.findViewById(R.id.ureaMK2);
        kclMH = view.findViewById(R.id.kclMH);
        kclMK1 = view.findViewById(R.id.kclMK1);
        kclMK2 = view.findViewById(R.id.kclMK2);
        ponskaMH = view.findViewById(R.id.phonskaMH);
        ponskaMK1 = view.findViewById(R.id.phonskaMK1);
        ponskaMK2 = view.findViewById(R.id.phonskaMK2);
        sp36MH = view.findViewById(R.id.sp36MH);
        sp36MK1 = view.findViewById(R.id.sp36MK1);
        sp36MK2 = view.findViewById(R.id.sp36MK2);
        zaMH = view.findViewById(R.id.zaMH);
        zaMK1 =  view.findViewById(R.id.zaMK1);
        zaMK2 =  view.findViewById(R.id.zaMK2);
        tanggal.setSpinnersShown(false);
//
//        getDataSpinner();
        gridLayout = view.findViewById(R.id.mainGrid);
        jenis_lahan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1)
                    gridLayout.setVisibility(View.VISIBLE);
                else{
                    gridLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//
//        edKab_kota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String URL_SELECT = "http://bptpjatim.com/selectSpinner.php";
//
//                final String kabupaten = edKab_kota.getSelectedView().toString().trim();
//
//
//                StringRequest stringRequest = new StringRequest(Request.Method.POST,
//                        URL_SELECT,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                try {
//
//                                    JSONObject jsonObject = new JSONObject(response);
//                                    JSONArray jsonArray = jsonObject.getJSONArray("result");
//                                    ArrayList<getData> list_data;
//                                    list_data = new ArrayList<>();
//                                    for (int i = 0; i < jsonArray.length(); i++) {
//                                        JSONObject hasil = jsonArray.getJSONObject(i);
//
//                                        String kabupaten = hasil.getString("kabupaten");
//
//                                        list_data.add(new getData(
//                                                kabupaten
//                                        ));
//                                        ArrayAdapter<getData> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, list_data);
//                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                        adapter.notifyDataSetChanged();
//                                        edKab_kota.setAdapter(adapter);
//
//
//
//                                    }
//
//
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
////                       Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                )
//                {
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<>();
//
//                        params.put("kabupaten",kabupaten);
//
//                        return params;
//                    }
//                };
//                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//                requestQueue.add(stringRequest);
//            }
//
//
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//
//
//        edKecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String URL_SELECT = "http://bptpjatim.com/selectSpinner.php";
//                final String kecamatan = edKecamatan.getSelectedView().toString().trim() + "";
//
//
//
//                StringRequest stringRequest = new StringRequest(Request.Method.POST,
//                        URL_SELECT,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                try {
//
//                                    JSONObject jsonObject = new JSONObject(response);
//                                    JSONArray jsonArray = jsonObject.getJSONArray("result");
//                                    ArrayList<getData> list_data;
//                                    list_data = new ArrayList<>();
//                                    for (int i = 0; i < jsonArray.length(); i++) {
//                                        JSONObject hasil = jsonArray.getJSONObject(i);
//
//                                        String kecamatan = hasil.getString("kecamatan");
//
//                                        list_data.add(new getData(
//                                                kecamatan
//                                        ));
//                                        ArrayAdapter<getData> adapter2 = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, list_data);
//                                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                        adapter2.notifyDataSetChanged();
//                                        edKecamatan.setAdapter(adapter2);
//
//
//
//                                    }
//
//
//
//                                } catch (JSONException e) {
////                                    e.printStackTrace();
//                                }
////                       Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
////                                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                )
//                {
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<>();
//
//
//                        params.put("kecamatan",kecamatan);
//
//
//
//
//
//
//                        return params;
//                    }
//                };
//                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//                requestQueue.add(stringRequest);
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        edDesa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String URL_SELECT = "http://bptpjatim.com/selectSpinner.php";
//
//                StringRequest stringRequest = new StringRequest(Request.Method.POST,
//                        URL_SELECT,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                try {
//                                    ArrayList<getData> list_data;
//                                    JSONObject jsonObject = new JSONObject(response);
//                                    JSONArray jsonArray = jsonObject.getJSONArray("result");
//
//                                    list_data = new ArrayList<>();
//                                    for (int i = 0; i < jsonArray.length(); i++) {
//                                        JSONObject hasil = jsonArray.getJSONObject(i);
//
//                                        String desa = hasil.getString("desa");
//
//                                        list_data.add(new getData(
//                                                desa
//                                        ));
//                                        ArrayAdapter<getData> adapter2 = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, list_data);
//                                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                        adapter2.notifyDataSetChanged();
//                                        edDesa.setAdapter(adapter2);
//
//
//
//                                    }
//
//
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
////                       Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//
//                            }
//                        }
//                )
//                {
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<>();
//
//
//
//
//
//
//
//                        return params;
//                    }
//                };
//                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//                requestQueue.add(stringRequest);
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
                Bitmap bitmap = ((ProfileActivity)getActivity()).getBitmap();
                image.setImageBitmap(bitmap);
            }
        });

        submit = view.findViewById(R.id.submit);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mName = name.getText().toString().trim();
                String mKab_kot = edKab_kota.getSelectedView().toString().trim();
                String mKecamatan = edKecamatan.getSelectedView().toString().trim();
                String mDesa = edDesa.getSelectedView().toString().trim();
//                if (!mName.isEmpty() && !mKab_kot.isEmpty() && !mKecamatan.isEmpty() && !mDesa.isEmpty()){
                input();
                Toast.makeText(getContext(), "Input Success!", Toast.LENGTH_SHORT);
//                }
                if (mName.equals("")){
                    name.setError("Empty");
                }

            }
        });










    }


    void getDataSpinner(){
        String URL_SELECT = "http://bptpjatim.com/selectSpinner.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_SELECT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("result2");
                            ArrayList<getData> list_data;
                            list_data = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hasil = jsonArray.getJSONObject(i);
                                String desa = hasil.getString("desa");
                                String kecamatan = hasil.getString("kecamatan");

                                String kabupaten = hasil.getString("kabupaten");

                                    list_data.add(new getData(
                                            kabupaten
                                    ));
                                    ArrayAdapter<getData> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, list_data);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    adapter.notifyDataSetChanged();
                                    edKab_kota.setAdapter(adapter);


                                    list_data.add(new getData(
                                            kecamatan
                                    ));
                                    ArrayAdapter<getData> adapter2 = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, list_data);
                                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                adapter2.notifyDataSetChanged();
                                    edKecamatan.setAdapter(adapter2);


                                    list_data.add(new getData(
                                            desa
                                    ));
                                    ArrayAdapter<getData> adapter3 = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, list_data);
                                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                adapter3.notifyDataSetChanged();
                                    edDesa.setAdapter(adapter3);


                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("desa", "widoro");
                params.put("kecamatan" , "donorojo");
                params.put("kabupaten", "pacitan");





                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GET_FROM_GALLERY);
//        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_FROM_GALLERY && resultCode == RESULT_OK && data!= null && data.getData() != null){
            filepath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filepath);
                image.setImageBitmap(bitmap);
            }catch (IOException e){

            }
        }
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

    private String imageToString(Bitmap bit){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void input(){

//         TextView tDesa = (TextView)this.desa.getSelectedView();
//        TextView tKab_kota = (TextView)this.kab_kota.getSelectedView();
        TextView tBahan_induk = (TextView)this.bahan_induk.getSelectedView();
        final double tKoordinatLat = aLocation.getLatitude();
        final double tKoordinatLang = aLocation.getLongitude();
        TextView tPenggunaan_lahan = (TextView)this.penggunaan_lahan.getSelectedView();
        TextView tRelief = (TextView)this.relief.getSelectedView();
        TextView tDrainase = (TextView)this.drainase.getSelectedView();
        TextView tReaksi_tanah = (TextView)this.reaksi_tanah.getSelectedView();
        TextView tTanaman_utama = (TextView)this.tanaman_utama.getSelectedView();
        TextView tKedalaman_olah = (TextView)this.kedalaman_olah.getSelectedView();
        TextView tDesa = (TextView)this.edDesa.getSelectedView();
        TextView tKab_kota =  (TextView)this.edKab_kota.getSelectedView();
        TextView tKecamatan = (TextView)this.edKecamatan.getSelectedView();
        TextView tPola_tanamMH = (TextView)this.pola_tanamanMH.getSelectedView();
        TextView tPola_tanamMK1 = (TextView)this.pola_tanamanMK1.getSelectedView();
        TextView tPola_tanamMK2 = (TextView)this.pola_tanamanMK2.getSelectedView();
        TextView tVarietasMH = (TextView)this.varietasMH.getSelectedView();
        TextView tVarietasMK1 = (TextView)this.varietasMK1.getSelectedView();
        TextView tVarietasMK2 = (TextView)this.varietasMK2.getSelectedView();



        final String nama = name.getText().toString().trim();
        final String etLereng = lereng.getText().toString().trim();
        final String desa = tDesa.getText().toString().trim();
        final String kab_kota = tKab_kota.getText().toString().trim();
        final String bahan_induk = tBahan_induk.getText().toString().trim();
        final String kecamatan = tKecamatan.toString().toString().trim();
        final String penggunaan_lahan = tPenggunaan_lahan.getText().toString().trim();
        final String relief = tRelief.getText().toString().trim();
        final String drainase = tDrainase.getText().toString().trim();
        final String reaksi_tanah = tReaksi_tanah.getText().toString().trim();
        final String tanaman_utama = tTanaman_utama.getText().toString().trim();
        final String kedalaman_olah = tKedalaman_olah.getText().toString().trim();
    //        final String pola_tanam = tPola_tanam.get.toString().trim();
    //        final String varietas = tPola_tanam.get.toString().trim();
    //        final String provitas = tProvitas.getText().toString().trim();
//        final String penggunaan_pupuk = tPenggunaan_pupuk.getText().toString().trim();

        final String koordinatLang = Double.toString(tKoordinatLang).trim();
        final String koordinatLat = Double.toString(tKoordinatLat).trim();
        final String catatan = edCatatan.getText().toString().trim();
        final String etPola_tanamMH = tPola_tanamMH.getText().toString().trim();
        final String etPola_tanamMK1 = tPola_tanamMK1.getText().toString().trim();
        final String etPola_tanamMK2 = tPola_tanamMK2.getText().toString().trim();
        final String etProvitasMH = provitasMH.getText().toString().trim();
        final String etProvitasMK1 = provitasMK1.getText().toString().trim();
        final String etProvitasMK2 = provitasMK2.getText().toString().trim();
        final String etVarietasMH = tVarietasMH.getText().toString().trim();
        final String etVarietasMK1 = tVarietasMK1.getText().toString().trim();
        final String etVarietasMK2 = tVarietasMK2.getText().toString().trim();
       final String etOrganikMH = organikMH.toString().trim();
       final String etOrganikMK1 = organikMK1.toString().trim();
       final String etOrganikMK2 = organikMK2.toString().trim();
       final String etUREAMH = ureaMH.toString().trim();
       final String etUREAMK1 = ureaMK1.toString().trim();
       final String etUREAMK2 = ureaMK2.toString().trim();
       final String etKclMH = kclMH.toString().trim();
       final String etKclMK1 = kclMK1.toString().trim();
       final String etKclMK2 = kclMK2.toString().trim();
       final String etPonskaMH = ponskaMH.toString().trim();
       final String etPonskaMK1 = ponskaMK1.toString().trim();
       final String etPonskaMK2 = ponskaMK2.toString().trim();
       final String etSp36MH = sp36MH.toString().trim();
       final String etSp36MK1 =sp36MK1.toString().trim();
       final String etSp36MK2 = sp36MK2.toString().trim();
       final String etZaMH = zaMH.toString().trim();
       final String etZaMK1 = zaMK1.toString().trim();
       final String etZaMK2 = zaMK2.toString().trim();
        final String foto = imageToString(bitmap);



        StringRequest stringRequest = new StringRequest(Request.Method.POST, INPUT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
//                            GeoJsonLayer layer = new GeoJsonLayer(mGoogleMap, R.raw.administrasi, getContext());
                            if (success.equals("1")){
//
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
                params.put("nama", extraNama);
                params.put("initial", nama);
                params.put("desa", desa);
                params.put("kab_kota", kab_kota);
                params.put("kecamatan", kecamatan);
                params.put("koordinat_lat", koordinatLat);
                params.put("koordinat_lang", koordinatLang);
                params.put("bahan_induk", bahan_induk);
                params.put("penggunaan_lahan", penggunaan_lahan);
                params.put("relief", relief);
                params.put("drainase", drainase);
                params.put("reaksi_tanah", reaksi_tanah);
                params.put("tanaman_utama", tanaman_utama);
                params.put("kedalaman_olah", kedalaman_olah);
                params.put("lereng", etLereng);
//                params.put("varietas", varietas);
//                params.put("provitas", provitasMH);
//                params.put("penggunaan_pupuk", penggunaan_pupuk);
                params.put("polatanamMH", etPola_tanamMH);
                params.put("polatanamMK1", etPola_tanamMK1);
                params.put("polatanamMK2", etPola_tanamMK2);
                params.put("varietasMH", etVarietasMH);
                params.put("varietasMK1", etVarietasMK1);
                params.put("varietasMK2", etVarietasMK2);
                params.put("provitasMH", etProvitasMH);
                params.put("provitasMK1", etProvitasMK1);
                params.put("provitasMK2", etProvitasMK2);
                params.put("organikMH", etOrganikMH);
                params.put("organikMK1", etOrganikMK1);
                params.put("organikMK2", etOrganikMK2);
                params.put("ureaMH", etUREAMH);
                params.put("ureaMK1", etUREAMK1);
                params.put("ureaMK2", etUREAMK2);
                params.put("kclMH", etKclMH);
                params.put("kclMK1", etKclMK1);
                params.put("kclMK2", etKclMK2);
                params.put("ponskaMH", etPonskaMH);
                params.put("ponskaMK1", etPonskaMK1);
                params.put("ponskaMK2", etPonskaMK2);
                params.put("sp36MH", etSp36MH);
                params.put("sp36MK1", etSp36MK1);
                params.put("sp36MK2", etSp36MK2);
                params.put("zaMH", etZaMH);
                params.put("zaMK1", etZaMK1);
                params.put("zaMK2", etZaMK2);
                params.put("foto", foto);
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
class getData{
    String nKabkotNo="", nProvno="", nKecNo="", nDesa="";
    getData(String kabKotNo, String provNo, String kecNo, String desaName){
        this.nKabkotNo = kabKotNo;
        this.nProvno = provNo;
        this.nKecNo = kecNo;
        this.nDesa = desaName;
    }

    getData(String input){
        this.nDesa = input;
    }

    public String getKota() {
        return nKabkotNo;
    }

    public String getProvinsi() {
        return nProvno;
    }

    public String getNama() {
        return nKecNo;
    }

    public String getDesa() { return nDesa; }

}

