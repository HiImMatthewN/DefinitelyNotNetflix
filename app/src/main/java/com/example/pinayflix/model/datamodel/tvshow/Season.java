package com.example.pinayflix.model.datamodel.tvshow;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Season {
    @SerializedName("air_data")
    private String airDate;
    @SerializedName("episode_count")
    private int episodeCount;
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("overview")
    private String overview;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("season_number")
    private String seasonNumber;
    @SerializedName("episodes")
    private ArrayList<Episode> episodes;

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public String getAirDate() {
        return airDate;
    }

    public int getEpisodeCount() {
        return episodeCount;
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

    public String getSeasonNumber() {
        return seasonNumber;
    }
}
