package com.example.pinayflix.model.datamodel.review;

import com.google.gson.annotations.SerializedName;

public class Author {
    @SerializedName("name")
    private String name;
    @SerializedName("username")
    private String userName;
    @SerializedName("avatar_path")
    private String avatarPath;
    @SerializedName("rating")
    private int rating;

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public int getRating() {
        return rating;
    }
}
