package com.example.sisuperjatim.ui.profile;

//import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.TextView;

import androidx.annotation.NonNull;
//import androidx.cardview.widget.CardView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

//import com.example.sisuperjatim.ProfileActivity;
import com.example.sisuperjatim.CaraPenggunaanActivity;
import com.example.sisuperjatim.HubungiActivity;
import com.example.sisuperjatim.InputFragment;
import com.example.sisuperjatim.MainActivity;
import com.example.sisuperjatim.ProfileActivity;
import com.example.sisuperjatim.R;
import com.example.sisuperjatim.SessionManager;
import com.example.sisuperjatim.TentangKamiActivity;

import java.util.HashMap;
//import java.time.Instant;

public class ProfileFragment extends Fragment {
    CardView cvTentangKami,cvCaraPenggunaan,cvHubungi,cvInputData,cvLogout;
    private ProfileViewModel profileViewModel;
    private TextView name, email;
    private CardView logout;
    SessionManager sessionManager;
    ImageView imageView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root ;
        root = inflater.inflate(R.layout.fragment_profile, container, false);


//        cvTentangKami.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        cvCaraPenggunaan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        cvHubungi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        cvInputData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentInputData = new Intent(getActivity(), ProfileActivity.class);
//                startActivity(intentInputData);
//            }
//        });






//        final TextView textView = root.findViewById(R.id.text_notifications);
//        profileViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });



        return root;
    }

//    @Override
//    public void onCreateView(@Nullable LayoutInflater inflater,
//                              ViewGroup container,
//                              Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        View view = inflater.inflate(R.layout.fragment_profile, container, false);
//        sessionManager = new SessionManager(getContext());
//
//
//        name = view.findViewById(R.id.name);
//        email = view.findViewById(R.id.email);
//        logout = view.findViewById(R.id.logout);
//
//        Intent intent = getActivity().getIntent();
//        String extraName = intent.getStringExtra("name");
//        String extraEmail = intent.getStringExtra("email");
//
//        name.setText(extraName);
//        email.setText(extraEmail);
//
//        HashMap<String, String> user = sessionManager.getUserData();
//        String mName = user.get(sessionManager.NAME);
//        String mEmail = user.get(sessionManager.EMAIL);
//
//        name.setText(mName);
//        email.setText(mEmail);
//
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sessionManager.logout();
//                Toast.makeText(getContext(), "LOGGED OUT!", Toast.LENGTH_SHORT);
//            }
//        });
//
//    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManager = new SessionManager(getContext());
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        logout = view.findViewById(R.id.logout);
//        cvInputData = view.findViewById(R.id.inputData);
        cvTentangKami = view.findViewById(R.id.tentangkami);
        cvCaraPenggunaan = view.findViewById(R.id.cara);
        cvHubungi = view.findViewById(R.id.hubungi);

        HashMap<String, String> user = sessionManager.getUserData();
        String extraName = user.get(sessionManager.NAME);
        String extraEmail = user.get(sessionManager.EMAIL);

        name.setText(extraName);
        email.setText(extraEmail);

        imageView = view.findViewById(R.id.ivBackkembali);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
                Toast.makeText(getContext(), "LOGGED OUT!", Toast.LENGTH_SHORT);
            }
        });

        cvTentangKami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInputData = new Intent(getActivity(), TentangKamiActivity.class);
                startActivity(intentInputData);
            }
        });

        cvCaraPenggunaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInputData = new Intent(getActivity(), CaraPenggunaanActivity.class);
                startActivity(intentInputData);
            }
        });

        cvHubungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInputData = new Intent(getActivity(), HubungiActivity.class);
                startActivity(intentInputData);
            }
        });

//        cvInputData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentInputData = new Intent(getActivity(), ProfileActivity.class);
//                startActivity(intentInputData);
////                Fragment intentInputData = new InputFragment();
////                FragmentTransaction transaction = getFragmentManager().beginTransaction();
////                transaction.replace(R.id.nav_host_fragment, intentInputData);
////                transaction.addToBackStack(null);
////                transaction.commit();
//            }
//        });
    }
}

