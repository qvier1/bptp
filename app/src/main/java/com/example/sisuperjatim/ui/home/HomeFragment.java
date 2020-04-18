package com.example.sisuperjatim.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sisuperjatim.MainActivity;
import com.example.sisuperjatim.ProfileActivity;
import com.example.sisuperjatim.R;
import com.example.sisuperjatim.ui.profile.ProfileFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Button hamburgerMenu;
    private TextView nama, kota, provinsi;
    private static String URL_SELECT = "http://bptpjatim.com/select.php";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        get_data();
        return root;


    }

    void get_data() {
        String URL_SELECT = "http://bptpjatim.com/select.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_SELECT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("result");
                            ArrayList<getData> list_data;
                            list_data = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hasil = jsonArray.getJSONObject(i);
                                String nama = hasil.getString("nama");
                                String kota = hasil.getString("kota");
                                String provinsi = hasil.getString("provinsi");

                                list_data.add(new getData(
                                        nama,
                                        kota,
                                        provinsi
                                ));
                            }

                            ListView listView = getView().findViewById(R.id.list_item);
                            CustomAdapter customAdapter = new CustomAdapter(getContext(), list_data);
                            listView.setAdapter(customAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        nama = view.findViewById(R.id.nama);
//        kota = view.findViewById(R.id.kota);
//        provinsi = view.findViewById(R.id.provinsi);
        hamburgerMenu = view.findViewById(R.id.hamburgerMenuProfile);
        hamburgerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Fragment mFrag = new ProfileFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.nav_host_fragment, mFrag);
//                transaction.addToBackStack(null);
//                transaction.commit();
                MainActivity.fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, new ProfileFragment(), null).addToBackStack(null).commit();


            }
        });
    }
}


class getData{
    String nNama="", nKota="", nProvinsi="";
    getData(String nama, String kota, String provinsi){
        this.nNama = nama;
        this.nKota = kota;
        this.nProvinsi = provinsi;
    }

    public String getKota() {
        return nKota;
    }

    public String getProvinsi() {
        return nProvinsi;
    }

    public String getNama() {
        return nNama;
    }
}

class CustomAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<getData> model;

    CustomAdapter(Context context, ArrayList<getData> model) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.model = model;
    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int position) {
        return model.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.list, null);
        TextView nama, kota, provinsi;
        nama = view.findViewById(R.id.nama);
        kota = view.findViewById(R.id.kota);
        provinsi = view.findViewById(R.id.provinsi);

        nama.setText("Nama pengguna  : "+ model.get(position).getNama());
        kota.setText(model.get(position).getKota());
        provinsi.setText("Provinsi  :"+model.get(position).getProvinsi());
        return view;
    }
}

