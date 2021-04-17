package com.example.pinayflix.model.datamodel.tvshow;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

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
    @SerializedName("seasons")
    private ArrayList<Season> seasons;
    @SerializedName("episode_run_time")
    private ArrayList<Integer> episodeRuntime;

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

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

    public ArrayList<Integer> getEpisodeRuntime() {
        return episodeRuntime;
    }
}
