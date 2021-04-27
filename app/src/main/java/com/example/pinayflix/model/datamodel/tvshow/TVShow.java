package com.example.pinayflix.model.datamodel.tvshow;

import com.example.pinayflix.model.datamodel.Item;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TVShow extends Item {

    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("popularity")
    private double popularity;
    @SerializedName("id")
    private int id;
    @SerializedName("backdrop_path")
    private String backDropPath;
    @SerializedName("overview")
    private String overview;
    @SerializedName("first_air_date")
    private String firstAirDate;
    @SerializedName("genre_ids")
    private ArrayList<Integer> genreIds;
    @SerializedName("name")
    private String title;
    @SerializedName("episode_run_time")
    private ArrayList<Integer> episodeRuntime;
    @SerializedName("seasons")
    private ArrayList<Season> seasons;
    @SerializedName("vote_average")
    private double voteAverage;
    @SerializedName("number_of_seasons")
    private int numOfSeasons;
    @SerializedName("vote_count")
    private int voteCount;

    public String getOverview() {
        return overview;
    }

    public ArrayList<Integer> getEpisodeRuntime() {
        return episodeRuntime;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getNumOfSeasons() {
        return numOfSeasons;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public int getId() {
        return id;
    }

    public String getBackDropPath() {
        return backDropPath;
    }


    public String getFirstAirDate() {
        return firstAirDate;
    }

    public ArrayList<Integer> getGenreIds() {
        return genreIds;
    }

    public String getTitle() {
        return title;
    }

    public int getVoteCount() {
        return voteCount;
    }
}
