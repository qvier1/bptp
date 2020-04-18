package com.example.sisuperjatim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.sisuperjatim.ui.maps.MapsFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends FragmentActivity {

    GoogleMap mGoogleMap;
    MapView mMapView, koordinat;
    View mView;
    private EditText nama;


    ImageView ivBackProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final InputFragment inputFragment = new InputFragment();
        ivBackProfile = findViewById(R.id.ivBackProfile);
        ivBackProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(i);


            }
        });






// bunch of unused codes



//        submit = findViewById(R.id.submit);
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                input();
//            }
//        });




    }

//    private void input(){
//
//
//        submit.setVisibility(View.GONE);
//        TextView tDesa = (TextView)this.desa.getSelectedView();
//        TextView tKab_kota = (TextView)this.kab_kota.getSelectedView();
//        TextView  tJenis_lahan = (TextView)this.jenis_lahan.getSelectedView();
//        TextView tBahan_induk = (TextView)this.bahan_induk.getSelectedView();
//        MapView tKoordinat = ;
//        TextView tPenggunaan_lahan = (TextView)this.penggunaan_lahan.getSelectedView();
//        TextView tRelief = (TextView)this.relief.getSelectedView();
//        TextView tKondisi_lahan =  (TextView)this.kondisi_lahan.getSelectedView();
//        TextView tDrainase = (TextView)this.drainase.getSelectedView();
//        TextView tReaksi_tanah = (TextView)this.reaksi_tanah.getSelectedView();
//        TextView tTanaman_utama = (TextView)this.tanaman_utama.getSelectedView();
//        TextView tTekstur = (TextView)this.tekstur.getSelectedView();
//        TextView tKedalaman_olah = (TextView)this.kedalaman_olah.getSelectedView();
//        TextView tPola_tanam = (TextView)this.pola_tanaman.getSelectedView();
//        TextView tVarietas = (TextView)this.varietas.getSelectedView();
//        TextView tProvitas = (TextView)this.provitas.getSelectedView();
//        TextView tPenggunaan_pupuk = (TextView)this.penggunaan_pupuk.getSelectedView();
//        TextView tKecamatan = (TextView)this.kecamatan.getSelectedView();
//
//        final String nama = this.nama.getText().toString().trim();
//        final String desa = tDesa.getText().toString().trim();
//        final String kab_kota = tKab_kota.getText().toString().trim();
//        final String jenis_lahan = tJenis_lahan.getText().toString().trim();
//        final String bahan_induk = tBahan_induk.getText().toString().trim();
//        final String pengguna_lahan = tPenggunaan_lahan.getText().toString().trim();
//        final String relief = tRelief.getText().toString().trim();
//        final String kondisi_lahan = tKondisi_lahan.getText().toString().trim();
//        final String drainase = tDrainase.getText().toString().trim();
//        final String reaksi_tanah = tReaksi_tanah.getText().toString().trim();
//        final String tanaman_utama = tTanaman_utama.getText().toString().trim();
//        final String tekstur = tTekstur.getText().toString().trim();
//        final String kedalaman_olah = tKedalaman_olah.getText().toString().trim();
//        final String pola_tanam = tPola_tanam.getText().toString().trim();
//        final String varietas = tVarietas.getText().toString().trim();
//        final String provitas = tProvitas.getText().toString().trim();
//        final String penggunaan_pupuk = tPenggunaan_pupuk.getText().toString().trim();
//        final String kecamatan = tKecamatan.getText().toString().trim();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, INPUT_URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String success = jsonObject.getString("success");
//                            if (success.equals("1")){
//
//                                Toast.makeText(ProfileActivity.this,"Success!",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        }catch (JSONException e){
//                            e.printStackTrace();
//                            Toast.makeText(ProfileActivity.this,
//                                    "Something's wrong :( \nDetails : \n" +
//                                            e.toString(),
//                                    Toast.LENGTH_SHORT).show();
//                            submit.setVisibility(View.VISIBLE);
//
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(ProfileActivity.this,
//                                "Something's wrong :( \nDetails : \n" +
//                                        error.toString(),
//                                Toast.LENGTH_SHORT).show();
//                        submit.setVisibility(View.VISIBLE);
//                    }
//                }
//        ){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("nama", nama);
//                params.put("kab_kota", kab_kota);
//                params.put("kecamatan", kecamatan);
//                params.put("desa", desa);
////                params.put("koordinat", koordinat);
//                params.put("jenis_lahan", jenis_lahan);
//                params.put("bahan_induk", bahan_induk);
//                params.put("penggunaan_lahan", pengguna_lahan);
//                params.put("relief", relief);
//                params.put("kondisi_lahan", kondisi_lahan);
//                params.put("drainase", drainase);
//                params.put("reaksi_tanah", reaksi_tanah);
//                params.put("tanaman_utama", tanaman_utama);
//                params.put("tekstur", tekstur);
//                params.put("kedalaman_olah", kedalaman_olah);
//                params.put("pola_tanaman", pola_tanam);
//                params.put("varietas", varietas);
//                params.put("provitas", provitas);
//                params.put("penggunaan_pupuk", penggunaan_pupuk);
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//
//    }


}
