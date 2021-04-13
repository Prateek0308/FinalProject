package com.project.recipesearch.models;


import java.util.ArrayList;

public class RecipeSearchModel
{
    private String href;

    private String title;

    private String version;

    private String lastSearchTxt;

    private ArrayList<Results> results;

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

    public String getVersion ()
    {
        return version;
    }

    public void setVersion (String version)
    {
        this.version = version;
    }

    public ArrayList<Results> getResults() {
        return results;
    }

    public void setResults(ArrayList<Results> results) {
        this.results = results;
    }

    public String getLastSearchTxt() {
        return lastSearchTxt;
    }

    public void setLastSearchTxt(String lastSearchTxt) {
        this.lastSearchTxt = lastSearchTxt;
    }

    @Override
    public String toString()
    {
        return "[href = "+href+", title = "+title+", version = "+version+", results = "+results+"]";
    }
}