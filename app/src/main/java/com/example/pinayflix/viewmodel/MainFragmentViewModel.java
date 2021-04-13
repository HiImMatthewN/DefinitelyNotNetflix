package com.example.pinayflix.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pinayflix.DataClassification;
import com.example.pinayflix.DataGenre;
import com.example.pinayflix.model.datamodel.trailer.Trailer;
import com.example.pinayflix.model.datamodel.movie.Movie;
import com.example.pinayflix.model.datamodel.tvshow.TVShow;
import com.example.pinayflix.model.datamodel.utilities.Event;
import com.example.pinayflix.repository.MovieRepository;
import com.example.pinayflix.repository.TVShowRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainFragmentViewModel extends ViewModel {
     MovieRepository movieRepository ;
    TVShowRepository tvShowRepository ;
    private String TAG = "MainFragmentViewModel";
    //Initialize Page count to 1;
    private int upcomingMoviesPageCount = 1;
    private int popularMoviesPageCount = 1;
    private int nowPlayingMoviesPageCount = 1;
    private int horrorMoviesPageCount = 1;
    private int documentariesPageCount = 1;
    private int romanceMoviesPageCount = 1;

    private int upcomingTvShowsPageCount = 1;
    private int popularTvShowsPageCount = 1;
    private int nowPlayingTvShowsPageCount = 1;
    private int mysteryTvShowsPageCount = 1;
    private int romanceTvShowsPageCount = 1;
    private int documentaryTvShowsPageCount = 1;


    private MutableLiveData<Event<Integer>> requestMovieDetailsLiveData = new MutableLiveData<>();
    private MutableLiveData<DataClassification> onDataClassificationChange = new MutableLiveData<>();
    private DataClassification currentSelectClassification;

    @Inject
    public MainFragmentViewModel(MovieRepository movieRepository,TVShowRepository tvShowRepository) {
        this.movieRepository = movieRepository;
        this.tvShowRepository = tvShowRepository;
        requestMovieData();
    }


    public void requestData(DataClassification dataClassification) {
        if(currentSelectClassification == dataClassification) return;
        if (dataClassification == DataClassification.MOVIE){
            requestMovieData();
            onDataClassificationChange.setValue(DataClassification.MOVIE);

        }
        else if (dataClassification == DataClassification.TV_SHOW){
            requestTvShowData();
            onDataClassificationChange.setValue(DataClassification.TV_SHOW);
        }

    }

    public void requestMovieData() {
        //Returns if Movie is already selected
        if (currentSelectClassification == DataClassification.MOVIE) return;
        requestUpcomingMovies(upcomingMoviesPageCount);
        requestPopularMovies(popularMoviesPageCount);
        requestNowPlayingMovies(nowPlayingMoviesPageCount);
//        requestLatestMovie();
        requestHorrorMovies("27,9648", horrorMoviesPageCount, "2014-01-01", 100);
        requestDocumentaries("99", documentariesPageCount, "2015-01-01", 250);
        requestRomanceMovies("10749", romanceMoviesPageCount, "2015-01-01", 250);

        currentSelectClassification = DataClassification.MOVIE;

    }

    private void requestTvShowData() {
        //Returns if TV Shows is already selected
        if (currentSelectClassification == DataClassification.TV_SHOW) return;
        requestPopularTvShows(popularTvShowsPageCount);
        requestUpComingTvShows(upcomingTvShowsPageCount);
        requestNowPlayingTvShows(nowPlayingTvShowsPageCount);
        requestMysteryTvShows("9648", mysteryTvShowsPageCount, "2010-01-01", 0);
        requestDocumentaryTvShows("99", documentaryTvShowsPageCount, "2010-01-01", 0);
        requestRomanceTvShows("10749", romanceTvShowsPageCount, "2010-01-01", 0);

        currentSelectClassification = DataClassification.TV_SHOW;

    }

    //Request Movies
    private void requestLatestMovie() {
        movieRepository.requestLatestMovie();
    }

    private void requestPopularMovies(int page) {
        movieRepository.requestPopularMovies(page);
    }

    private void requestUpcomingMovies(int page) {
        movieRepository.requestUpComingMovies(page);
    }

    private void requestNowPlayingMovies(int page) {
        movieRepository.requestNowPlayingMovies(page);
    }

    private void requestHorrorMovies(String genre, int page, String startReleaseDate, int voteCount) {
        movieRepository.requestHorrorMovies(genre, page, startReleaseDate, voteCount);

    }

    private void requestDocumentaries(String genre, int page, String startReleaseDate, int voteCount) {
        movieRepository.requestDocumentaryMovies(genre, page, startReleaseDate, voteCount);

    }

    private void requestRomanceMovies(String genre, int page, String startReleaseDate, int voteCount) {
        movieRepository.requestRomanceMovies(genre, page, startReleaseDate, voteCount);

    }


    //Request TV Shows
    private void requestPopularTvShows(int page) {
        tvShowRepository.requestPopularTvShows(page);

    }

    private void requestUpComingTvShows(int page) {
        tvShowRepository.requestUpcomingTvShows(page);

    }

    private void requestNowPlayingTvShows(int page) {
        tvShowRepository.requestNowPlayingTvShows(page);

    }

    private void requestMysteryTvShows(String genre, int page, String firstAirDate, int voteCount) {
        tvShowRepository.requestMysteryTvShows(genre, page, firstAirDate, voteCount);
    }

    private void requestRomanceTvShows(String genre, int page, String firstAirDate, int voteCount) {
        tvShowRepository.requestRomanceTvShows(genre, page, firstAirDate, voteCount);
    }

    private void requestDocumentaryTvShows(String genre, int page, String firstAirDate, int voteCount) {
        tvShowRepository.requestDocumentaryTvShows(genre, page, firstAirDate, voteCount);
    }

    public void requestMovieTrailer(int movieId) {
        movieRepository.requestVideos(movieId);
    }
    public void requestTvShowTrailer(int tvShowId) {
        tvShowRepository.requestTvShowTrailer(tvShowId);
    }
    public LiveData<List<Movie>> getPopularMovies() {
        return movieRepository.getPopularMoviesLiveData();
    }

    public LiveData<List<Movie>> getUpcomingMovies() {
        return movieRepository.getUpcomingMoviesLiveData();

    }

    public LiveData<List<Movie>> getHorrorMovies() {
        return movieRepository.getHorrorMoviesLiveData();

    }

    public LiveData<List<Movie>> getDocumentaries() {
        return movieRepository.getDocumentaryLiveData();

    }

    public LiveData<List<Movie>> getRomanceMovies() {
        return movieRepository.getRomanceMoviesLiveData();
    }

    public LiveData<List<Movie>> getNowPlayingMovies() {
        return movieRepository.getNowPlayingMoviesLiveData();
    }

    public void requestNewData(DataGenre classification) {

        switch (classification) {
            case Popular:
                popularMoviesPageCount++;
                movieRepository.requestNewPopularMovies(popularMoviesPageCount);
                break;
            case Upcoming:
                upcomingMoviesPageCount++;
                movieRepository.requestNewUpcomingMovies(upcomingMoviesPageCount);
                break;
            case Horror:
                horrorMoviesPageCount++;
                movieRepository.requestNewMovieByGenre("28,9648", horrorMoviesPageCount, "2014-01-01", 100);
                break;

            case Romance:
                romanceMoviesPageCount++;
                movieRepository.requestNewMovieByGenre("10749", romanceMoviesPageCount, "2015-01-01", 250);
                break;

            case Documentary:
                documentariesPageCount++;
                movieRepository.requestNewMovieByGenre("99", documentariesPageCount, "2015-01-01", 250);

        }
    }

    public void requestMovieDetails(int movieId) {
        requestMovieDetailsLiveData.setValue(new Event<>(movieId));
    }

    public LiveData<List<Movie>> getNewRequestedMovie() {
        return movieRepository.getRequestedNewMoviesLiveData();
    }

    public LiveData<List<Trailer>> getMovieTrailer() {
        return movieRepository.getMovieTrailerLiveData();
    }

    public LiveData<Movie> getLatestMovie() {
        return movieRepository.getLatestMovieLiveData();
    }

    public LiveData<Event<Integer>> onMovieDetailsSelected() {
        return requestMovieDetailsLiveData;
    }


    public LiveData<List<TVShow>> getPopularTvShows() {
        return tvShowRepository.getPopularTvShowsLiveData();
    }

    public LiveData<List<TVShow>> getUpcomingTvShows() {
        return tvShowRepository.getUpComingTvShowsLiveData();
    }

    public LiveData<List<TVShow>> getNowPlayingTvShows() {
        return tvShowRepository.getNowPlayingTvShowsLiveData();
    }

    public LiveData<List<TVShow>> getHorrorTvShows() {
        return tvShowRepository.getMysteryTvShowsLiveData();
    }

    public LiveData<List<TVShow>> getRomanceTvShows() {
        return tvShowRepository.getRomanceTvShowsLiveData();
    }

    public LiveData<List<TVShow>> getDocumentaryTvShows() {
        return tvShowRepository.getDocumentaryTvShowsLiveData();
    }
    public LiveData<List<Trailer>> getTvShowTrailer(){
        return tvShowRepository.getTvShowTrailerLiveData();
    }

    public LiveData<DataClassification> getOnDataClassification(){
        return onDataClassificationChange;
    }

}
