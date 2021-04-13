package com.project.recipesearch.activities;


import android.os.Bundle;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.project.recipesearch.R;
import com.project.recipesearch.models.Results;
import com.project.recipesearch.utils.Constants;

import java.util.ArrayList;

import io.paperdb.Paper;

public class FavoriteRecipeActivity extends BaseActivity implements RecipeSearchAdapter.ItemClickListener{

    ArrayList<Results> recipeResultsList = new ArrayList();
    RecipeSearchAdapter recipeSearchAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_recipe);


        RecyclerView favoriteRecycler = findViewById(R.id.favourite_recycler);

        ImageView backIcon = findViewById(R.id.ic_back_click);

        ArrayList<Results> localList = Paper.book().read(Constants.FAVOURITE_LIST);

        if (localList != null){
            recipeResultsList = localList;
        }

        recipeSearchAdapter = new RecipeSearchAdapter(recipeResultsList, this);
        favoriteRecycler.setAdapter(recipeSearchAdapter);
        backIcon.setOnClickListener(v->{
            finish();
        });
    }

    @Override
    public void onItemClick(Results recipeModel) {

    }

    @Override
    public void likedClicked(int position, Boolean isFlag) {
        if (!isFlag) {


            ArrayList<Results> localList = Paper.book().read(Constants.FAVOURITE_LIST);
            ArrayList<Results> newRecipeList = new ArrayList<>();

            for (Results mRecipe : localList) {
                if (mRecipe.getTitle().equals(recipeResultsList.get(position).getTitle()) && mRecipe.getIngredients().equals(recipeResultsList.get(position).getIngredients())) {
                } else {
                    newRecipeList.add(mRecipe);
                }
            }

            localList = newRecipeList;

            Paper.book().write(Constants.FAVOURITE_LIST, localList);

            recipeResultsList.remove(recipeResultsList.get(position));
            recipeSearchAdapter.notifyItemChanged(position);
        }
    }
}