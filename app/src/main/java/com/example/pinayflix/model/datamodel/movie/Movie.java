package com.example.pinayflix.model.datamodel.movie;

import com.example.pinayflix.model.datamodel.Genre;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Movie {
    @SerializedName("id")
    private int movieId;
    @SerializedName("title")
    private String title;
    @SerializedName("genres")
    private ArrayList<Genre> genres;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("overview")
    private String overview;
    @SerializedName("runtime")
    private int runTime;
    @SerializedName("genre_ids")
    private ArrayList<Integer> genreIds;

    public double getVoteAverage() {
        return voteAverage;
    }

    @SerializedName("vote_average")
    private double voteAverage;

    public String getOverview() {
        return overview;
    }

    public int getRunTime() {
        return runTime;
    }

    public ArrayList<Integer> getGenreIds() {
        return genreIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getMovieId() {
        return movieId;
    }
}
