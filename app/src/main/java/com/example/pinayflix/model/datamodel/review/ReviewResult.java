package com.example.pinayflix.model.datamodel.review;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReviewResult {

    @SerializedName("id")
    private int id;
    @SerializedName("page")
    private int page;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("results")
    private ArrayList<Review> reviews;

    public int getId() {
        return id;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }
}
