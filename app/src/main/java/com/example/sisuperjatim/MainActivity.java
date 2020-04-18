package com.example.sisuperjatim;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sisuperjatim.ui.home.HomeFragment;
import com.example.sisuperjatim.ui.maps.MapsFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kishan.askpermission.ErrorCallback;
import com.kishan.askpermission.PermissionCallback;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.kishan.askpermission.PermissionInterface;

import androidx.fragment.app.FragmentManager;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class MainActivity extends AppCompatActivity implements PermissionCallback, ErrorCallback {
    public static final int PERMISSION_REQUEST_CODE = 1;
    public static FragmentManager fragmentManager;
    HomeFragment homeFragment = new HomeFragment();


    SessionManager sessionManager;

    BottomNavigationView btnNav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fragmentManager = getSupportFragmentManager();



        btnNav = findViewById(R.id.nav_view);
        btnNav.setOnNavigationItemSelectedListener(navListener);
        btnNav.getMenu().findItem(R.id.navigation_home).setEnabled(false);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HomeFragment.newInstance();
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


        } else {
            requeststoragepermission();
        }

    }

    private void requeststoragepermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION)) {
            new AlertDialog.Builder(this).setTitle("Permission Needed").setMessage("This Permission is needed").create().show();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "PERMISSIION GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "PERMISSIION DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }


    void switchFragment(@NonNull Fragment fragment, boolean addToBackStack) {
        final String NAME = fragment.getClass().getName();
        final FragmentManager fm = getSupportFragmentManager();

        final boolean fragmentPopped = fm.popBackStackImmediate(NAME, 0);

        if (fragmentPopped || fm.findFragmentByTag(NAME) != null) {
            return;
        }

        if (addToBackStack) {
            fm.beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment, NAME)
                    .commit();
        } else {
            fm.beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment, NAME)
                    .addToBackStack(NAME)
                    .commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @RequiresApi(api = Build.VERSION_CODES.P)
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                    switch (menuItem.getItemId()) {
                        case R.id.navigation_home:
                            btnNav.getMenu().findItem(R.id.navigation_home).setEnabled(false);
                            btnNav.getMenu().findItem(R.id.navigation_maps).setEnabled(true);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, homeFragment, null).addToBackStack(null).commit();

                            break;
                        case R.id.navigation_maps:
                            btnNav.getMenu().findItem(R.id.navigation_home).setEnabled(true);
                            btnNav.getMenu().findItem(R.id.navigation_maps).setEnabled(false);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, new MapsFragment(), null).addToBackStack(null).detach(homeFragment).commit();

                            break;
                    }


                    return true;

                }
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




