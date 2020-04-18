package com.example.sisuperjatim;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sisuperjatim.ui.home.HomeFragment;
import com.example.sisuperjatim.ui.maps.MapsFragment;
import com.example.sisuperjatim.ui.profile.ProfileFragment;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kishan.askpermission.AskPermission;
import com.kishan.askpermission.ErrorCallback;
import com.kishan.askpermission.PermissionCallback;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.kishan.askpermission.ErrorCallback;
import com.kishan.askpermission.PermissionCallback;
import com.kishan.askpermission.PermissionInterface;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.baidu.mapapi.BMapManager.getContext;


public class MainActivity extends AppCompatActivity implements PermissionCallback, ErrorCallback {
    public static final int PERMISSION_REQUEST_CODE = 1;
    public static FragmentManager fragmentManager;




    SessionManager sessionManager;
    GoogleMap gMap;
    Button hamburgerMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




//        get_data();
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_maps, R.id.navigation_profile)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
////        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);

//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        fragmentManager = getSupportFragmentManager();


        BottomNavigationView btnNav = findViewById(R.id.nav_view);
        btnNav.setOnNavigationItemSelectedListener(navListener);
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


        } else {
            requeststoragepermission();
        }

    }










//    void get_data () {
//        String URL_SELECT = "http://bptpjatim.com/select.php";
//        StringRequest stringRequest = new StringRequest(Request.Method.POST,
//                URL_SELECT,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            JSONArray jsonArray = jsonObject.getJSONArray("result");
//                            ArrayList<getData> list_data = new ArrayList<>();
//                            for (int i = 0; i<jsonArray.length(); i++){
//                                JSONObject hasil = jsonArray.getJSONObject(i);
//                                String nama = hasil.getString("nama");
//                                String kota = hasil.getString("kota");
//                                String provinsi = hasil.getString("provinsi");
//
//                                list_data.add(new getData(
//                                   nama,
//                                   kota,
//                                   provinsi
//                                ));
//                            }
//
//                            ListView listView = findViewById(R.id.list_item);
//                            CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, list_data);
//                            listView.setAdapter(customAdapter) ;
//
//                        }catch (JSONException e){
//                            e.printStackTrace();
//                        }
////                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//
//    }






    private void requeststoragepermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION)){
            new AlertDialog.Builder(this).setTitle("Permission Needed").setMessage("This Permission is needed").create().show();

        }else{
            ActivityCompat.requestPermissions(this, new String[] {ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"PERMISSIION GRANTED", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"PERMISSIION DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //    private boolean checkPermission() {
//        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
//
//
//        return result == PackageManager.PERMISSION_GRANTED;
//    }
//
//    private void requestPermission() {
//
//        ActivityCompat.requestPermissions(this, new String[] {ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE );
//
//    }





    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;


                    switch (menuItem.getItemId()){
                        case R.id.navigation_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.navigation_maps:
                            selectedFragment = new MapsFragment();
                            break;
//                        case R.id.hamburgerMenuProfile:
//                            selectedFragment = new ProfileFragment();
//
//                            break;
                }
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
                return true;
            }

//    public void selectLogin(View view) {
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);
//    }

    };

    @Override
    public void onShowRationalDialog(PermissionInterface permissionInterface, int requestCode) {

    }

    @Override
    public void onShowSettings(PermissionInterface permissionInterface, int requestCode) {

    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    @Override
    public void onPermissionsDenied(int requestCode) {

    }
}
//
//class getData{
//    String nNama="", nKota="", nProvinsi="";
//    getData(String nama, String kota, String provinsi){
//        this.nNama = nama;
//        this.nKota = kota;
//        this.nProvinsi = provinsi;
//    }
//
//    public String getKota() {
//        return nNama;
//    }
//
//    public String getProvinsi() {
//        return nProvinsi;
//    }
//
//    public String getNama() {
//        return nProvinsi;
//    }
//}
//
//class CustomAdapter extends BaseAdapter {
//    Context context;
//    LayoutInflater layoutInflater;
//    ArrayList<getData> model;
//    CustomAdapter(Context context, ArrayList<getData> model){
//        this.context = context;
//        this.model = model;
//    }
//    @Override
//    public int getCount() {
//        return model.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return model.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = layoutInflater.inflate(R.layout.list, null);
//        TextView nama, kota, provinsi;
//        nama = view.findViewById(R.id.nama);
//        kota = view.findViewById(R.id.kota);
//        provinsi = view.findViewById(R.id.provinsi);
//
//        nama.setText(model.get(position).getNama());
//        kota.setText(model.get(position).getKota());
//        provinsi.setText(model.get(position).getProvinsi());
//        return view;
//    }
//}





