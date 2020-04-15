package com.example.sisuperjatim;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sisuperjatim.ui.home.HomeFragment;
import com.example.sisuperjatim.ui.maps.MapsFragment;
import com.example.sisuperjatim.ui.profile.ProfileFragment;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kishan.askpermission.AskPermission;
import com.kishan.askpermission.ErrorCallback;
import com.kishan.askpermission.PermissionCallback;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.kishan.askpermission.ErrorCallback;
import com.kishan.askpermission.PermissionCallback;
import com.kishan.askpermission.PermissionInterface;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class MainActivity extends AppCompatActivity implements PermissionCallback, ErrorCallback{
    private static final int PERMISSION_REQUEST_CODE = 1;

    SessionManager sessionManager;
    GoogleMap gMap;
    private Button hamburgerMenu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_maps, R.id.navigation_profile)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
////        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);

        BottomNavigationView btnNav = findViewById(R.id.nav_view);
        btnNav.setOnNavigationItemSelectedListener(navListener);
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){


        }else{
            requeststoragepermission();
        }

    }
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
//                        case R.id.navigation_profile:
//                            sessionManager.checkLogin();
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
