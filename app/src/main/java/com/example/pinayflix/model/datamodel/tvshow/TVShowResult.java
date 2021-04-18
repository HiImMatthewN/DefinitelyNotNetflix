package com.example.pinayflix.model.datamodel.tvshow;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TVShowResult {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private ArrayList<TVShow> tvShows;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public int getPage() {
        return page;
    }

    public ArrayList<TVShow> getTvShows() {
        return tvShows;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
