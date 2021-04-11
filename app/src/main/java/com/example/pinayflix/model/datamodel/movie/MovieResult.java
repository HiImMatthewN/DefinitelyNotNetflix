package com.example.pinayflix.model.datamodel.movie;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieResult {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private ArrayList<Movie> movies;


    public int getPage() {
        return page;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }
}
