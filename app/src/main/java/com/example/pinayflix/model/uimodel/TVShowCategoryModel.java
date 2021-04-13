package com.example.pinayflix.model.uimodel;

import com.example.pinayflix.DataGenre;
import com.example.pinayflix.model.datamodel.tvshow.TVShow;

import java.util.List;

public class TVShowCategoryModel {
    private DataGenre categoryName;
    private List<TVShow> tvShows;

    public TVShowCategoryModel(DataGenre categoryName, List<TVShow> movies) {
        this.categoryName = categoryName;
        this.tvShows = movies;
    }

    public DataGenre getCategoryName() {
        return categoryName;
    }

    public List<TVShow> getTVShows() {
        return tvShows;
    }

    public void setMovies(List<TVShow> tvShows) {
        this.tvShows = tvShows;
    }
}
