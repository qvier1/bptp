package com.example.sisuperjatim;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;


    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String NAME = "NAME";
    public static final String EMAIL = "EMAIL";
    public static final String LATITUDE = "-7.9637127";
    public static final String LONGITUDE = "112.6131008";


    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession (String name, String email){
        editor.putBoolean(LOGIN, true);
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.apply();
    }

    public void createSessionMap(Double latitude, Double longitude){
        String tLatitude = latitude.toString();
        String tLongitude = longitude.toString();
        editor.putString(LATITUDE, tLatitude);
        editor.putString(LONGITUDE, tLongitude);
        editor.apply();
    }

    public boolean is_Loggin(){
        return sharedPreferences.getBoolean(LOGIN,false);
    }

    public void checkLogin(){
        if (!is_Loggin()){
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
            ((MainActivity)context).finish();
        }
    }

    public HashMap<String, String> getUserData(){
        HashMap<String, String > user = new HashMap<>();
        user.put(NAME, sharedPreferences.getString(NAME, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));

        return user;
    }

    public HashMap<String, String> getUserMapData(){
        HashMap<String, String > user = new HashMap<>();
        user.put(LATITUDE, sharedPreferences.getString(LATITUDE, "-7.9637127"));
        user.put(LONGITUDE, sharedPreferences.getString(LONGITUDE, "112.6131008"));
        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
        ((ProfileActivityLogged)context).finish();
    }

}
