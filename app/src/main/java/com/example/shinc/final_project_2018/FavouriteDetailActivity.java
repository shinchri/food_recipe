package com.example.shinc.final_project_2018;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class FavouriteDetailActivity extends AppCompatActivity {

    TextView tvTitle, tvIngredient;
    Button btnWebsite, btnFav;
    public static MyAppDatabase myAppDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_detail);

        // allowMainThreadQueries() allow queries to be carried out in the main thread
        myAppDatabase = Room.databaseBuilder(getApplicationContext(), MyAppDatabase.class,
                "userdb").allowMainThreadQueries().build();

        tvTitle = findViewById(R.id.tvTitle);
        tvIngredient = findViewById(R.id.tvIngredient);
        btnWebsite = findViewById(R.id.btnWebsite);
        btnFav = findViewById(R.id.btnFav);

        // retrieving data passed from MainActivity
        Intent intent = getIntent();
        final String title = intent.getStringExtra("title").trim();
        final String href = intent.getStringExtra("href"); // had to be final to be used in inner class
        final String ingredients = intent.getStringExtra("ingredient").trim();
        final String thumbnail = intent.getStringExtra("thumbnail");

        tvTitle.setText(title);
        tvIngredient.setText(ingredients);

        btnWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check for internet connection
                ConnectivityManager cm =
                        (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                if(isConnected) {
                    Uri webpage = Uri.parse(href);
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    if(intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
                else {
                    Toast.makeText(FavouriteDetailActivity.this,
                            "There are no internet connection. Please try again.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Favourite> mylist = FavouriteDetailActivity.myAppDatabase.myDao().getFavouritesWithTitle(title);

                if(mylist.size() > 0) {
                    Favourite favourite = mylist.get(0);
                    FavouriteDetailActivity.myAppDatabase.myDao().deleteFavourite(favourite);

                    Toast.makeText(FavouriteDetailActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(FavouriteDetailActivity.this, "Already Deleted!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set Detail Activity Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Recipe Detail");
        actionBar.setSubtitle(title);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
