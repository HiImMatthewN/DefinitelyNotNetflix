package com.example.pinayflix.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.pinayflix.model.datamodel.movie.Movie;
import com.example.pinayflix.model.datamodel.review.Review;
import com.example.pinayflix.repository.MovieRepository;
import com.example.pinayflix.ui.fragments.MovieDetailsFragment;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MovieDetailsFragmentViewModel extends ViewModel {
    private MovieRepository movieRepository;
    private List<Review> reviews;
    private int movieId;
    private String TAG = "MovieDetailsFragmentViewModel";


    @Inject
    public MovieDetailsFragmentViewModel(SavedStateHandle savedStateHandle, MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        Log.d(TAG, "new ViewModelCreated");
        movieId = savedStateHandle.get(MovieDetailsFragment.DETAILS_KEY);

        requestMovieDetails();
        requestSimilarMovies();
    }

    public void requestReviews() {
        movieRepository.requestReviews(movieId);
    }

    private void requestMovieDetails() {
        movieRepository.requestMovieDetails(movieId);
    }

    public void requestSimilarMovies() {
        movieRepository.requestRecos(movieId);


    }


    public LiveData<Movie> getMovieDetails() {
        return movieRepository.getMovieDetailsLiveData();
    }

    public LiveData<List<Review>> getReviews() {
        return movieRepository.getReviewsLiveData();

    }


    public Review getReviewFromPos(int pos) {
        return reviews.get(pos);
    }

    public LiveData<List<Movie>> getSimilarMovies() {
        return movieRepository.getMovieRecommendations();
    }
}
