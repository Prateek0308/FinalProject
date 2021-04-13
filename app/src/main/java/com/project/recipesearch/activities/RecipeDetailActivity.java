package com.project.recipesearch.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.recipesearch.R;
import com.project.recipesearch.models.Results;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Results recipeRes = (Results) getIntent().getSerializableExtra("object");

        ((ImageView)findViewById(R.id.ic_back_click)).setOnClickListener(v-> finish());
        ImageView recipeImage = findViewById(R.id.recipe_image);

        Glide.with(this).load(recipeRes.getThumbnail()).into(recipeImage);
        ((TextView)findViewById(R.id.dish_title)).setText(recipeRes.getTitle().trim());
        ((TextView)findViewById(R.id.ingredients_txt)).setText(recipeRes.getIngredients().trim());
        ((TextView)findViewById(R.id.href_link_txt)).setText(recipeRes.getHref());

        ((TextView)findViewById(R.id.href_link_txt)).setOnClickListener(v->{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(recipeRes.getHref()));
            startActivity(browserIntent);
        });


    }
}