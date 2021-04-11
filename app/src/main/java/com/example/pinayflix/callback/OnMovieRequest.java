package com.example.pinayflix.callback;

import com.example.pinayflix.model.datamodel.movie.Movie;

public interface OnMovieRequest {
    void onMovieSelected(Movie movie);
}
