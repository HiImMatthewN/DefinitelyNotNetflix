package com.example.pinayflix.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pinayflix.MovieClassification;
import com.example.pinayflix.model.datamodel.utilities.Event;
import com.example.pinayflix.model.datamodel.movie.Movie;
import com.example.pinayflix.model.datamodel.Video;
import com.example.pinayflix.repository.MovieRepository;

import java.util.List;

public class MainFragmentViewModel extends ViewModel {
    private MovieRepository movieRepository = new MovieRepository();
    private String TAG = "MainFragmentViewModel";
    //Initialize Page count to 1;
    private int upcomingMoviesPageCount = 1;
    private int popularMoviesPageCount = 1;
    private int nowPlayingMoviesPageCount = 1;
    private int horrorMoviesPageCount = 1;
    private int documentariesPageCount = 1;
    private int romanceMoviesPageCount = 1;

    private MutableLiveData<Event<Integer>> requestMovieDetailsLiveData = new MutableLiveData<>();


    public MainFragmentViewModel() {
        Log.d(TAG, "MainFragmentViewModel: Created");
        requestUpcomingMovies(upcomingMoviesPageCount);
        requestPopularMovies(popularMoviesPageCount);
        requestNowPlayingMovies(nowPlayingMoviesPageCount);
//        requestLatestMovie();
        requestHorrorMovies("28,9648",horrorMoviesPageCount,"2014-01-01",100);
        requestDocumentaries("99",documentariesPageCount,    "2015-01-01",250);
        requestRomanceMovies("10749",romanceMoviesPageCount,    "2015-01-01",250);
    }
    private void requestLatestMovie(){
        movieRepository.requestLatestMovie();
    }
    private void requestPopularMovies(int page){
        movieRepository.requestPopularMovies(page);
    }
    private void requestUpcomingMovies(int page){
        movieRepository.requestUpComingMovies(page);
    }
    private void requestNowPlayingMovies(int page){
        movieRepository.requestNowPlayingMovies(page);
    }
    private void requestHorrorMovies(String genre, int page,String startReleaseDate,int voteCount){
        movieRepository.requestHorrorMovies(genre,page,startReleaseDate,voteCount);

    }
    private void requestDocumentaries(String genre, int page,String startReleaseDate,int voteCount){
        movieRepository.requestDocumentaryMovies(genre,page,startReleaseDate,voteCount);

    }
    private void requestRomanceMovies(String genre, int page,String startReleaseDate,int voteCount){
        movieRepository.requestRomanceMovies(genre,page,startReleaseDate,voteCount);

    }
    public void requestVideo(int movieId){
        movieRepository.requestVideos(movieId);
    }
    public LiveData<List<Movie>> getPopularMovies(){
        return movieRepository.getPopularMoviesLiveData();
    }
    public LiveData<List<Movie>> getUpcomingMovies(){
        return movieRepository.getUpcomingMoviesLiveData();

    }
    public LiveData<List<Movie>> getHorrorMovies(){
        return movieRepository.getHorrorMoviesLiveData();

    }
    public LiveData<List<Movie>> getDocumentaries(){
        return movieRepository.getDocumentaryLiveData();

    }
    public LiveData<List<Movie>> getRomanceMovies(){
        return movieRepository.getRomanceMoviesLiveData();
    }
    public LiveData<List<Movie>> getNowPlayingMovies(){
        return movieRepository.getNowPlayingMoviesLiveData();
    }
    public void requestNewData(MovieClassification classification){

        switch (classification){
            case Popular:
                popularMoviesPageCount++;
                movieRepository.requestNewPopularMovies(popularMoviesPageCount);
                break;
            case Upcoming:
                upcomingMoviesPageCount ++;
                movieRepository.requestNewUpcomingMovies(upcomingMoviesPageCount);
                break;
            case Horror:
                horrorMoviesPageCount++;
                movieRepository.requestNewMovieByGenre("28,9648",horrorMoviesPageCount,"2014-01-01",100);
                break;

            case Romance:
                romanceMoviesPageCount++;
                movieRepository.requestNewMovieByGenre("10749",romanceMoviesPageCount,"2015-01-01",250);
                break;

            case Documentary:
                documentariesPageCount++;
                movieRepository.requestNewMovieByGenre("99",documentariesPageCount,"2015-01-01",250);

        }
    }

    public void requestMovieDetails(int movieId){
            requestMovieDetailsLiveData.setValue(new Event<>(movieId));
    }

    public LiveData<List<Movie>> getNewRequestedMovie(){
        return movieRepository.getRequestedMovies();
    }
    public LiveData<List<Video>> getMovieVideos(){
        return movieRepository.getMovieVideos();
    }
    public LiveData<Movie> getLatestMovie(){
        return movieRepository.getLatestMovieLiveData();
    }
    public LiveData<Event<Integer>> onMovieDetailsSelected(){
        return requestMovieDetailsLiveData;
    }
}
