package com.example.shinc.final_project_2018;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.ItemClicked{
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Recipe> arrayList = new ArrayList<>();
    JSONArray jsonArray;
    int count;
    String json_url = "http://www.recipepuppy.com/api/?i=onions,garlic&q=omelet&p=3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        makeJSONRequest();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(" Recipe Lists");
        actionBar.setSubtitle(" Yum!");
        actionBar.setIcon(R.drawable.recepe);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

    }

    public void makeJSONRequest(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, json_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            jsonArray = response.getJSONArray("results");

                            count = 0;
                            arrayList.clear();
                            while(count < jsonArray.length()) {
                                try {
                                    Recipe recipe = new Recipe(jsonArray.getJSONObject(count).getString("title"),
                                            jsonArray.getJSONObject(count).getString("href"),
                                            jsonArray.getJSONObject(count).getString("ingredients"),
                                            jsonArray.getJSONObject(count).getString("thumbnail"));
                                    arrayList.add(recipe);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                count++;
                            }

                            adapter = new RecyclerAdapter(MainActivity.this, arrayList);
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

    @Override
    public void onItemClicked(int index) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);

        Recipe recipe = arrayList.get(index);
        intent.putExtra("title", recipe.getTitle());
        intent.putExtra("ingredient", recipe.getIngredients());
        intent.putExtra("href", recipe.getHref());

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.login:
                Toast.makeText(this, "login clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(this, "settings clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.refresh:
                Toast.makeText(this, "refresh clicked", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
