package com.example.sisuperjatim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.sisuperjatim.ui.profile.ProfileFragment;

public class CaraPenggunaanActivity extends AppCompatActivity {

    ImageView ivBackCaraPengunaan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cara_penggunaan);

        ivBackCaraPengunaan = findViewById(R.id.ivBackCaraPenggunaan);
        ivBackCaraPengunaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CaraPenggunaanActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
