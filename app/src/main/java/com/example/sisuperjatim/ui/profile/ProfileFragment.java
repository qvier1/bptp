package com.example.sisuperjatim.ui.profile;

//import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
//import android.widget.TextView;

import androidx.annotation.NonNull;
//import androidx.cardview.widget.CardView;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

//import com.example.sisuperjatim.ProfileActivity;
import com.example.sisuperjatim.ProfileActivity;
import com.example.sisuperjatim.R;
//import java.time.Instant;

public class ProfileFragment extends Fragment {
    CardView cardView;
    private ProfileViewModel profileViewModel;
    private TextView name, email;
    private Button logout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);


//        final TextView textView = root.findViewById(R.id.text_notifications);
//        profileViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        cardView = root.findViewById(R.id.input_data);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TestInput", "onClick: ");
                startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });
        return root;
    }



}

