package com.example.pinayflix.model.uimodel;

import com.example.pinayflix.MovieClassification;
import com.example.pinayflix.model.datamodel.movie.Movie;

import java.util.List;

public class MovieCategoryModel {
    private MovieClassification categoryName;
    private List<Movie> movies;

    public MovieCategoryModel(MovieClassification categoryName, List<Movie> movies) {
        this.categoryName = categoryName;
        this.movies = movies;
    }

    public MovieClassification getCategoryName() {
        return categoryName;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
