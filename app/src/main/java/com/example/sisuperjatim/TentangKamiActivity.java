package com.example.sisuperjatim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.sisuperjatim.ui.profile.ProfileFragment;

public class TentangKamiActivity extends AppCompatActivity {

    ImageView ivBackTentangKami;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang_kami);

        ivBackTentangKami = findViewById(R.id.ivBackTentangKami);
        ivBackTentangKami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TentangKamiActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
