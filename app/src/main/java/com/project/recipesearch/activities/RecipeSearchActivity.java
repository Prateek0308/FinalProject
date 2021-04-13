package com.project.recipesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.project.recipesearch.R;
import com.project.recipesearch.models.RecipeSearchModel;
import com.project.recipesearch.models.Results;
import com.project.recipesearch.networkCall.GetDataService;
import com.project.recipesearch.networkCall.RetrofitClientInstance;
import com.project.recipesearch.utils.Constants;

import java.util.ArrayList;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeSearchActivity extends BaseActivity implements RecipeSearchAdapter.ItemClickListener {

    ArrayList<Results> recipeResultsList = new ArrayList();
    RecipeSearchAdapter recipeSearchAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);

        RecyclerView recyclerView = findViewById(R.id.recipe_recycler);
        TextView noDataFound = findViewById(R.id.no_data_found_txt);
        ImageView goToFav = findViewById(R.id.go_to_favorite_recipes);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        RecipeSearchModel mRecipeSearchModel = Constants.retrieveLastResult(getPreferences(MODE_PRIVATE));
        if (mRecipeSearchModel != null) {
            ((EditText) findViewById(R.id.search_edit_text)).setText(mRecipeSearchModel.getLastSearchTxt());
            recipeResultsList = mRecipeSearchModel.getResults();
        }
        recipeSearchAdapter = new RecipeSearchAdapter(recipeResultsList, this);
        recyclerView.setAdapter(recipeSearchAdapter);
        ((EditText) findViewById(R.id.search_edit_text)).setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard(RecipeSearchActivity.this);
                Call<RecipeSearchModel> call = service.getSearchRecipes("http://www.recipepuppy.com/api/?i=" + v.getText().toString().toLowerCase());
                customProgressDialog(true, "Please Wait...");
                call.enqueue(new Callback<RecipeSearchModel>() {
                    @Override
                    public void onResponse(Call<RecipeSearchModel> call, Response<RecipeSearchModel> response) {
                        customProgressDialog(false, "Please Wait...");

                        if (response.body() != null) {
                            RecipeSearchModel recipeSearchModel = response.body();
                            if (recipeSearchModel.getResults().size() > 0) {
                                recyclerView.setVisibility(View.VISIBLE);
                                noDataFound.setVisibility(View.GONE);
                                recipeResultsList = recipeSearchModel.getResults();
                                recipeSearchModel.setLastSearchTxt(v.getText().toString());
                                Constants.storeLastResult(getPreferences(MODE_PRIVATE), recipeSearchModel);
                                recipeSearchAdapter = new RecipeSearchAdapter(recipeResultsList, RecipeSearchActivity.this);
                                recyclerView.setAdapter(recipeSearchAdapter);
                            } else {
                                recyclerView.setVisibility(View.GONE);
                                noDataFound.setVisibility(View.VISIBLE);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<RecipeSearchModel> call, Throwable t) {
                        customProgressDialog(false, "Please wait...");
                        Toast.makeText(RecipeSearchActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            }
            return false;
        });

        goToFav.setOnClickListener(v-> startActivity(new Intent(this, FavoriteRecipeActivity.class)));


    }

    @Override
    public void onItemClick(Results recipeModel) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra("object", recipeModel);
        startActivity(intent);
    }

    @Override
    public void likedClicked(int position, Boolean isFlag) {
        recipeResultsList.get(position).setFavorite(isFlag);
        recipeSearchAdapter.notifyDataSetChanged();

        if (isFlag) {
            ArrayList<Results> localList = Paper.book().read(Constants.FAVOURITE_LIST);
            if (localList != null && !localList.isEmpty()) {

                Boolean myFlag = false;
                for (Results mRecipe : localList) {
                    if (mRecipe.getTitle().equals(recipeResultsList.get(position).getTitle()) && mRecipe.getIngredients().equals(recipeResultsList.get(position).getIngredients())) {
                        myFlag = true;
                    }
                }
                if (myFlag) {
                    new MaterialAlertDialogBuilder(this)
                            .setMessage("This record already save in Database")
                            .setPositiveButton("Ok", (dialogInterface, i) -> {
                                dialogInterface.dismiss();
                            })
                            .show();
                } else {

                    localList.add(recipeResultsList.get(position));
                    Paper.book().write(Constants.FAVOURITE_LIST, localList);

                }
            } else {
                if (localList == null || localList.size() == 0) {
                    ArrayList<Results> newRecipeList = new ArrayList<>();
                    newRecipeList.add(recipeResultsList.get(position));
                    localList = newRecipeList;
                } else
                    localList.add(recipeResultsList.get(position));
                Paper.book().write(Constants.FAVOURITE_LIST, localList);
            }

            Log.e("Like List Size == > ", String.valueOf(localList.size()));

        } else {

            Log.e("isUnlike == > ", "true");
            ArrayList<Results> localList = Paper.book().read(Constants.FAVOURITE_LIST);
            ArrayList<Results> newRecipeList = new ArrayList<>();

            for (Results mRecipe : localList) {
                if (mRecipe.getTitle().equals(recipeResultsList.get(position).getTitle()) && mRecipe.getIngredients().equals(recipeResultsList.get(position).getIngredients())) {
                    Log.e("contains === > ", " true ");
                } else {
                    newRecipeList.add(mRecipe);
                }
            }

            localList = newRecipeList;
            Log.e("newListSize == > ", String.valueOf(localList.size()));
            Paper.book().write(Constants.FAVOURITE_LIST, localList);
            Log.e("listSize == > ", String.valueOf(localList.size()));
        }
    }
}