package com.example.pinayflix.model.datamodel.tvshow;

import com.google.gson.annotations.SerializedName;

public class Episode {
    @SerializedName("air_date")
    private String airDate;
    @SerializedName("episode_number")
    private int episodeNumber;
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("overview")
    private String overview;
    @SerializedName("still_path")
    private String stillPath;

    public String getStillPath() {
        return stillPath;
    }

    public String getAirDate() {
        return airDate;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
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
}
