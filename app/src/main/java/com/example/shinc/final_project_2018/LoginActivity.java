package com.example.shinc.final_project_2018;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText etLoginUser, etLoginPass;
    private CardView cvLogin;
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // setting the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Log In");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        etLoginUser = findViewById(R.id.etLoginUser);
        etLoginPass = findViewById(R.id.etLoginPass);
        cvLogin = findViewById(R.id.cvLogin);
        tvRegister = findViewById(R.id.tvReginster);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Register Link Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        cvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etLoginUser.getText().toString().trim();
                String password = etLoginPass.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    // No user input
                    Toast.makeText(LoginActivity.this,
                            "Please provide username and password.",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    // proceed to check database for object with the given username
                }
            }
        });
    }
}
