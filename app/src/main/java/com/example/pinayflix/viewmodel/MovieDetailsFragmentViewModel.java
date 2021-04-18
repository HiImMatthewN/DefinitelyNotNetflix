package com.example.pinayflix.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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
    private MutableLiveData<Review> reviewLiveData;
    private MutableLiveData<Integer> reviewCount;
    private List<Review> reviews;
    private int movieId;
    private String TAG = "MovieDetailsFragmentViewModel";


    @Inject
    public MovieDetailsFragmentViewModel( SavedStateHandle savedStateHandle, MovieRepository movieRepository){
        this.movieRepository = movieRepository;
        Log.d(TAG, "new ViewModelCreated");
        reviewLiveData = new MutableLiveData<>();
        reviewCount = new MutableLiveData<>();
        movieId =  savedStateHandle.get(MovieDetailsFragment.DETAILS_KEY);

        requestMovieDetails(movieId);
        requestReviews(movieId);
        requestSimilarMovies(movieId);
    }
    private void requestReviews(int movieId){
        movieRepository.requestReviews(movieId);
    }
    private void requestMovieDetails(int movieId){
        movieRepository.requestMovieDetails(movieId);
    }
    private void requestSimilarMovies(int movieId){
        movieRepository.requestRecos(movieId);


    }



    public LiveData<Movie> getMovieDetails(){
        return movieRepository.getMovieDetailsLiveData();
    }

    public LiveData<Integer> getReviewsCount(){
        LiveData<List<Review>> liveData = movieRepository.getReviewsLiveData();
        liveData.observeForever(reviews -> {

            //filter list here
            if(reviews != null &&reviews.size() >0 ){
                this.reviews = reviews;
                reviewCount.postValue(reviews.size());

            }else
            reviewCount.postValue(0);


        });
        return reviewCount;
    }


    public Review getReviewFromPos(int pos){
        return reviews.get(pos);
    }
    public LiveData<List<Movie>> getSimilarMovies(){
       return movieRepository.getMovieRecommendations();
    }
}
