package com.example.sisuperjatim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class ProfileActivityLogged extends AppCompatActivity {
SessionManager sessionManager;
private TextView name, email;
private Button logout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile_logged);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
//        logout = findViewById(R.id.logout);
//
        Intent intent = getIntent();
        String extraName = intent.getStringExtra("name");
        String extraEmail = intent.getStringExtra("email");

        name.setText(extraName);
        name.setText(extraEmail);

        HashMap<String, String> user = sessionManager.getUserData();
        String mName = user.get(sessionManager.NAME);
        String mEmail = user.get(sessionManager.EMAIL);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
            }
        });


    }


}
