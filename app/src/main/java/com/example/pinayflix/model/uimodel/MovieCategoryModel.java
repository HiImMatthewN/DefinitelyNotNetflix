package com.example.pinayflix.model.uimodel;

import com.example.pinayflix.DataGenre;
import com.example.pinayflix.model.datamodel.movie.Movie;

import java.util.List;

public class MovieCategoryModel {
    private DataGenre categoryName;
    private List<Movie> movies;

    public MovieCategoryModel(DataGenre categoryName, List<Movie> movies) {
        this.categoryName = categoryName;
        this.movies = movies;
    }

    public DataGenre getCategoryName() {
        return categoryName;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
