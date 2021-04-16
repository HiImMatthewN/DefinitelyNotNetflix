package com.example.pinayflix.model.datamodel.tvshow;

import com.google.gson.annotations.SerializedName;

public class TVShowDetails {


    @SerializedName("backdrop_path")
    private String backDropPath;
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("overview")
    private String overview;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("number_of_seasons")
    private int numOfSeasons;
    @SerializedName("vote_average")
    private double voteAverage;

    public String getBackDropPath() {
        return backDropPath;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public int getNumOfSeasons() {
        return numOfSeasons;
    }

    public double getVoteAverage() {
        return voteAverage;
    }
}
