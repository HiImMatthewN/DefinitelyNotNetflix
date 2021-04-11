package com.example.pinayflix.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pinayflix.model.datamodel.movie.MovieDetails;
import com.example.pinayflix.model.datamodel.review.Review;
import com.example.pinayflix.repository.MovieRepository;

import java.util.List;

public class MovieDetailsFragmentViewModel extends ViewModel {
    private MovieRepository movieRepository = new MovieRepository();
    private MutableLiveData<Review> reviewLiveData;
    private MutableLiveData<Integer> reviewCount;
    private List<Review> reviews;
    private String TAG = "MovieDetailsFragmentViewModel";



    public MovieDetailsFragmentViewModel(int movieId){
        Log.d(TAG, "new ViewModelCreated");
        reviewLiveData = new MutableLiveData<>();
        reviewCount = new MutableLiveData<>();
        requestMovieDetails(movieId);
        requestReviews(movieId);
    }
    private void requestReviews(int movieId){
        movieRepository.requestReviews(movieId);
    }
    private void requestMovieDetails(int movieId){
        movieRepository.requestMovieDetails(movieId);
    }




    public LiveData<MovieDetails> getMovieDetails(){
        return movieRepository.getMovieDetailsLiveData();
    }

    public LiveData<Integer> getReviewsCount(){
        LiveData<List<Review>> liveData = movieRepository.getReviewsLiveData();
        liveData.observeForever(reviews -> {
            Log.d(TAG, "Review Count" + reviews.size());

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
}
