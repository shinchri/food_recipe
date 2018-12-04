package com.example.shinc.final_project_2018;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class FavouriteActivity extends AppCompatActivity implements RecyclerAdapter.ItemClicked {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Favourite> arrayFavList;
    ArrayList<Recipe> arrayList;
    public static MyAppDatabase myAppDatabase;

    public static final String PREF_TEXT_SIZE = "pref_text_size";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        recyclerView = findViewById(R.id.recyclerFavView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        // allowMainThreadQueries() allow queries to be carried out in the main thread
        myAppDatabase = Room.databaseBuilder(getApplicationContext(), MyAppDatabase.class,
                "userdb").allowMainThreadQueries().build();

        // setting the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(" Favourite Recipe Lists");
        actionBar.setIcon(R.drawable.recepe);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // fill the items in the recycler view
        fillList();
    }

    @Override
    public void onItemClicked(int index) {
        Intent intent = new Intent(FavouriteActivity.this, FavouriteDetailActivity.class);

        Recipe recipe = arrayList.get(index);
        intent.putExtra("title", recipe.getTitle().trim());
        intent.putExtra("ingredient", recipe.getIngredients().trim());
        intent.putExtra("href", recipe.getHref().trim());
        intent.putExtra("thumbnail", recipe.getThumbnail().trim());

        startActivity(intent);
    }

    // fills the recycler view list
    public void fillList() {
        // retrieves the data from the Favourite table
        arrayFavList = (ArrayList)FavouriteActivity.myAppDatabase.myDao().getFavourites();

        int size = arrayFavList.size();

        // arrayList will be passed into adapter
        arrayList = new ArrayList<>();

        // size > 0 when there is at least one item in the list
        if(size > 0) {
            for(Favourite favourite: arrayFavList) {
                Recipe recipe = new Recipe(favourite.getTitle(),
                        favourite.getHref(),
                        favourite.getIngredients(),
                        favourite.getThumbnail());

                arrayList.add(recipe);
            }

            // check the text size setting
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(FavouriteActivity.this);
            String searchType = sharedPreferences.getString(PREF_TEXT_SIZE, "");

            float textSize;

            if(searchType.equals("12sp")) {
                textSize = 12;
            }
            else if (searchType.equals("14sp")) {
                textSize = 14;
            }
            else {
                textSize = 18;
            }

            // this is where the actual list is populated
            adapter = new RecyclerAdapter(FavouriteActivity.this, arrayList, textSize);
            recyclerView.setAdapter(adapter);
        }
        else {
            Toast.makeText(this, "No Favourites", Toast.LENGTH_SHORT).show();
        }
    }
}
