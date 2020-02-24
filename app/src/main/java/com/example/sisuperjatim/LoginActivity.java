package com.example.sisuperjatim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sisuperjatim.ui.profile.ProfileFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText username,password;
    private Button login;
    private TextView link_register;
    private static String URL_LOGIN = "http://192.168.1.5/bptp/login.php";
    SessionManager sessionManager;
    ImageView imageViewBaackLgin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager = new SessionManager(this);


        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        link_register = findViewById(R.id.link_register);
        login = findViewById(R.id.login);
        imageViewBaackLgin = findViewById(R.id.ivBackLogin);


        imageViewBaackLgin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mUsername = username.getText().toString().trim();
                String mPassword = password.getText().toString().trim();

                if (!mUsername.isEmpty() || !mPassword.isEmpty()){
                    Login(mUsername, mPassword);
                }else{
                    username.setError("Username is empty!");
                    password.setError("Password is empty!");
                }
            }
        });

        link_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }

    private void Login(final String username, final String password){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")){
                                for (int i = 0; i < jsonArray.length(); i ++ ){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String name = object.getString("name").trim();
                                    String email = object.getString("email").trim();
                                    sessionManager.createSession(name, email);
                                    Toast.makeText(LoginActivity.this, "Logged in! \n"
                                            , Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, ProfileFragment.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("email", email);
                                    Intent home = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(home);
                                }

                            }else if(success.equals("0")){
                                Toast.makeText(LoginActivity.this, "Wrong username or password! \n"
                                        , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Something's wrong\nDetails : "+
                                    e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Something's wroong\nDetails : "+ error.toString(), Toast.LENGTH_SHORT);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
