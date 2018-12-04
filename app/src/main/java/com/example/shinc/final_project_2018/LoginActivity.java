package com.example.shinc.final_project_2018;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText etLoginUser, etLoginPass;
    private CardView cvLogin;
    private TextView tvRegister;
    public static MyAppDatabase myAppDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // allowMainThreadQueries() allow queries to be carried out in the main thread
        myAppDatabase = Room.databaseBuilder(getApplicationContext(), MyAppDatabase.class,
                "userdb").allowMainThreadQueries().build();

        // setting the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Log In");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        etLoginUser = findViewById(R.id.etLoginUser);
        etLoginPass = findViewById(R.id.etLoginPass);
        cvLogin = findViewById(R.id.cvLogin);
        tvRegister = findViewById(R.id.tvReginster);

        // goes to register page
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Register Link Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        // check for login information
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
                    List<User> list_of_user = LoginActivity.myAppDatabase.myDao().getUsersWithName(username);

                    // check if the user exists
                    if(list_of_user.size() == 0) {
                        Toast.makeText(LoginActivity.this,
                                "The user does not exist, please check spelling or create a new user.",
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        // check for correct password
                        User user = list_of_user.get(0);
                        if(!user.getPassword().equals(password)) {
                            Toast.makeText(LoginActivity.this, "The password was no correct, please try again.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            // start main activity
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("username", username);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }
        });
    }
}
