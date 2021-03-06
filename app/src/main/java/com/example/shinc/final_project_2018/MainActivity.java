package com.example.shinc.final_project_2018;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.ItemClicked, SearchView.OnQueryTextListener {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Recipe> arrayList = new ArrayList<>();
    JSONArray jsonArray;
    int count;
    String json_url = "http://www.recipepuppy.com/api/?i=&q=&p=3"; // initial api request

    public static final String PREF_TEXT_SIZE = "pref_text_size";
    public static final String PREF_SEARCH_TYPE = "pref_search_type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        // set the default values
        PreferenceManager.setDefaultValues(this, R.xml.preference, false);

        // setting the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(" Recipe Lists");
        actionBar.setSubtitle(" Welcome!");
        actionBar.setIcon(R.drawable.recepe);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // calling makeJSONRequest method to fill the list with food menu
        makeJSONRequest();
    }

    // Makes use of the Volly to read json data
    // the advantage of using Volley (one of them) is that strict ordering is followed
    public void makeJSONRequest(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, json_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // get the array of results which resides in the object
                            jsonArray = response.getJSONArray("results");

                            // repopulate the arraylist
                            count = 0;
                            arrayList.clear();
                            while(count < jsonArray.length()) {
                                try {
                                    Recipe recipe = new Recipe(jsonArray.getJSONObject(count).getString("title").trim(),
                                            jsonArray.getJSONObject(count).getString("href").trim(),
                                            jsonArray.getJSONObject(count).getString("ingredients").trim(),
                                            jsonArray.getJSONObject(count).getString("thumbnail").trim());
                                    arrayList.add(recipe);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                count++;
                            }

                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
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
                            adapter = new RecyclerAdapter(MainActivity.this, arrayList, textSize);
                            recyclerView.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.getStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("chris", "volley error in MainActivity");
            }
        });

        MySingleton.getmInstance(MainActivity.this).addToRequestque(jsonObjectRequest);
    }

    // this function runs when the item in the list is clicked
    // starts DetailActivity with given index (order of item)
    @Override
    public void onItemClicked(int index) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);

        Recipe recipe = arrayList.get(index);
        intent.putExtra("title", recipe.getTitle().trim());
        intent.putExtra("ingredient", recipe.getIngredients().trim());
        intent.putExtra("href", recipe.getHref().trim());
        intent.putExtra("thumbnail", recipe.getThumbnail().trim());

        startActivity(intent);
    }

    // needed for actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // adding option menu
        getMenuInflater().inflate(R.menu.main, menu);

        // adding search function to the action bar
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    // event upon query submit
    @Override
    public boolean onQueryTextSubmit(String s) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String searchType = sharedPreferences.getString(PREF_SEARCH_TYPE, "");

        String userInput = s.toLowerCase();

        if(searchType.equals("title")) {
            json_url = "http://www.recipepuppy.com/api/?i=&q=" + userInput;
        }
        else {
            json_url = "http://www.recipepuppy.com/api/?i=" + userInput + "&q=";
        }

        // sends api request
        makeJSONRequest();

        return true;
    }

    // event fires as the user types into the search
    @Override
    public boolean onQueryTextChange(String s) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String searchType = sharedPreferences.getString(PREF_SEARCH_TYPE, "");

        String userInput = s.toLowerCase();

        if(searchType.equals("title")) {
            json_url = "http://www.recipepuppy.com/api/?i=&q=" + userInput;
        }
        else {
            json_url = "http://www.recipepuppy.com/api/?i=" + userInput + "&q=";
        }

        // sends api request
        makeJSONRequest();
        return true;
    }

    // Figures out which item is selected from the action bar,
    //  and takes appropriate action.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch(item.getItemId()) {
            case R.id.setting:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.favourite:
                intent = new Intent(this, FavouriteActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
