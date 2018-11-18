package com.example.shinc.final_project_2018;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private EditText etRegUsername, etRegPass, etRegRePass;
    private CardView cvLogin;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etRegUsername = findViewById(R.id.etRegUsername);
        etRegPass = findViewById(R.id.etRegPass);
        etRegRePass = findViewById(R.id.etRegRePass);
        tvLogin = findViewById(R.id.tvLogin);
        cvLogin = findViewById(R.id.cvLogin);

        // setting the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Register Here");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignUpActivity.this, "Go Back to Login Page.",
                        Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        cvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check for all user input required
                String username = etRegUsername.getText().toString().trim();
                String password = etRegPass.getText().toString().trim();
                String repass = etRegRePass.getText().toString().trim();

                if(username.isEmpty() || password.isEmpty() || repass.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please provide input for all fields.",
                            Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(repass)) {
                    Toast.makeText(SignUpActivity.this, "Please make sure the passwords match",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    // check data base for the username to see if it exist
                    // if it does let the user know
                    // else save the user data, and let the user know.
                }
            }
        });
    }
}
