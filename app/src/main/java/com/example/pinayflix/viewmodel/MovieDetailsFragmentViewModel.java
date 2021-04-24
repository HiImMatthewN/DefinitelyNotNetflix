package com.example.pinayflix.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.pinayflix.model.datamodel.SavedItem;
import com.example.pinayflix.model.datamodel.movie.Movie;
import com.example.pinayflix.model.datamodel.review.Review;
import com.example.pinayflix.repository.MovieRepository;
import com.example.pinayflix.repository.SavedItemRepository;
import com.example.pinayflix.ui.fragments.MovieDetailsFragment;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MovieDetailsFragmentViewModel extends ViewModel {
    private MovieRepository movieRepository;
    private SavedItemRepository savedItemRepository;
    private List<Review> reviews;
    private int movieId;
    private String TAG = "MovieDetailsViewModel";


    @Inject
    public MovieDetailsFragmentViewModel( SavedStateHandle savedStateHandle, MovieRepository movieRepository,SavedItemRepository savedItemRepository) {
        Log.d(TAG, "new ViewModelCreated");
        this.movieRepository = movieRepository;
        this.savedItemRepository = savedItemRepository;
        movieId = savedStateHandle.get(MovieDetailsFragment.DETAILS_KEY);
        savedItemRepository.checkIfSavedItemExists(movieId);

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



    public void addMovieToList(Movie movie) {
        Log.d(TAG, "addMovieToList: Adding Movie...");
        savedItemRepository.insertSavedItem(new SavedItem(movie.getMovieId()
                , movie.getTitle(), movie.getPosterPath()));
    }

    public void removeMovieFromList(Movie movie) {
        Log.d(TAG, "removeMovieFromList: Removing Movie....");
        savedItemRepository.deleteSavedItem(new SavedItem(movieId, movie.getTitle(), movie.getPosterPath()));

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

    public LiveData<Boolean> getIfMovieExistsFromList() {
        return savedItemRepository.getSavedItemExists();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        savedItemRepository.disposeSubscribers();
    }
}
