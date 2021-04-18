package com.example.pinayflix.model.datamodel.trailer;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TrailerResult {
    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private ArrayList<Trailer> trailers;

    public int getId() {
        return id;
    }

    public ArrayList<Trailer> getTrailers() {
        return trailers;
    }
}
