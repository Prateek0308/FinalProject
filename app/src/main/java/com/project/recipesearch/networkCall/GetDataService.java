package com.project.recipesearch.networkCall;

import com.project.recipesearch.models.RecipeSearchModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface GetDataService {

    @GET
    Call<RecipeSearchModel> getSearchRecipes(@Url String url);

}
