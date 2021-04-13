package com.project.recipesearch.models;


import java.io.Serializable;

public class Results implements Serializable
{
    private String thumbnail;

    private String ingredients;

    private String href;

    private String title;

    private boolean isFavorite = false;

    public String getThumbnail ()
    {
        return thumbnail;
    }

    public void setThumbnail (String thumbnail)
    {
        this.thumbnail = thumbnail;
    }

    public String getIngredients ()
    {
        return ingredients;
    }

    public void setIngredients (String ingredients)
    {
        this.ingredients = ingredients;
    }

    public String getHref ()
    {
        return href;
    }

    public void setHref (String href)
    {
        this.href = href;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public String toString()
    {
        return "[thumbnail = "+thumbnail+", ingredients = "+ingredients+", href = "+href+", title = "+title+"]";
    }
}
