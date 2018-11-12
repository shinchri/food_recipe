package com.example.shinc.final_project_2018;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView tvTitle, tvWebsite, tvIngredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tvTitle);
        tvWebsite = findViewById(R.id.tvWeb);
        tvIngredient = findViewById(R.id.tvIngredient);

        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String href = intent.getStringExtra("href");
        String ingredients = intent.getStringExtra("ingredient");

        tvTitle.setText(title);
        tvIngredient.setText(ingredients);
        tvWebsite.setText(href);
    }
}
