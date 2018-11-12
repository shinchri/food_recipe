package com.example.shinc.final_project_2018;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, json_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("chris", response.toString());
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
        Toast.makeText(MainActivity.this, arrayList.get(index).getTitle(), Toast.LENGTH_SHORT).show();
    }
}
