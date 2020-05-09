package com.example.sisuperjatim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.sisuperjatim.ui.profile.ProfileFragment;

public class HubungiActivity extends AppCompatActivity {

    ImageView ivBackHubungi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hubungi);



        ivBackHubungi = findViewById(R.id.ivBackHubungi);
        ivBackHubungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HubungiActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
