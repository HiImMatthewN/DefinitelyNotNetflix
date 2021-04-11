package com.example.pinayflix.model.datamodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VideoResult {
    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private ArrayList<Video> videos;

    public int getId() {
        return id;
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }
}
