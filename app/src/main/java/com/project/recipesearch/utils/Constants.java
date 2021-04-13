package com.project.recipesearch.utils;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.project.recipesearch.models.RecipeSearchModel;

public class Constants {
    public static final String FAVOURITE_LIST = "com.recipesearch.utils.favorite";
    private static final String LAST_RESULT = "com.recipesearch.utils.LAST.RESULT";

    public static void storeLastResult(SharedPreferences getPref, RecipeSearchModel recipeSearchModel){
        SharedPreferences mPrefs = getPref;
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(recipeSearchModel);
        prefsEditor.putString(LAST_RESULT, json);
        prefsEditor.commit();
    }

    public static RecipeSearchModel retrieveLastResult(SharedPreferences getPref){
        Gson gson = new Gson();
        String json = getPref.getString(LAST_RESULT, "");
        RecipeSearchModel obj = gson.fromJson(json, RecipeSearchModel.class);
        return obj;
    }
}
