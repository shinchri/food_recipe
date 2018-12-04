package com.example.shinc.final_project_2018;

import android.arch.persistence.room.Room;
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
    public static MyAppDatabase myAppDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // allowMainThreadQueries() allow queries to be carried out in the main thread
        myAppDatabase = Room.databaseBuilder(getApplicationContext(), MyAppDatabase.class,
                "userdb").allowMainThreadQueries().build();

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

        // goes back to login page
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignUpActivity.this, "Go Back to Login Page.",
                        Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        // check for user input and register once validated
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
                else if(password.length() < 8){
                    // check the password length is equal to or greater than 8
                    Toast.makeText(SignUpActivity.this, "Please make sure the password is 8 characters or more",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    // check data base for the username to see if it exist
                    // if it does let the user know
                    // else save the user data, and let the user know.
                    int size = SignUpActivity.myAppDatabase.myDao().getUsersWithName(username).size();

                    // the size will be either 1 or zero (since user_name column is checked for uniqueness
                    if(size == 1) {
                        Toast.makeText(SignUpActivity.this, "The username already exists.",
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        // create the user
                        User user = new User();
                        user.setName(username);
                        user.setPassword(password);

                        // Save the user to database
                        SignUpActivity.myAppDatabase.myDao().addUser(user);

                        Toast.makeText(SignUpActivity.this, "The user sign up was successful.",
                                Toast.LENGTH_SHORT).show();

                        finish();
                    }
                }
            }
        });
    }
}
