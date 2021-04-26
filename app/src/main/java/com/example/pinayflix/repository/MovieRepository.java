package com.example.pinayflix.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pinayflix.model.datamodel.movie.Movie;
import com.example.pinayflix.model.datamodel.review.Review;
import com.example.pinayflix.model.datamodel.trailer.Trailer;
import com.example.pinayflix.network.MovieService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieRepository {

    private MovieService movieService;


    // LiveData For Initialization
    private MutableLiveData<List<Movie>> popularMoviesLiveData= new MutableLiveData<>();
    private MutableLiveData<List<Movie>> upcomingMoviesLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> horrorMoviesLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> documentaryLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> romanceMoviesLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> nowPlayingLiveData = new MutableLiveData<>();

    private MutableLiveData<Movie> latestMovieLiveData = new MutableLiveData<>();

    //Video
    private MutableLiveData<List<Trailer>> movieVideosLiveData = new MutableLiveData<>();

    //LiveData for Updating
    private MutableLiveData<List<Movie>> requestNewMoviesLiveData = new MutableLiveData<>();

    //LiveData for MovieDetails
    private MutableLiveData<Movie> movieDetailsLiveData = new MutableLiveData<>();

    //Reviews
    private MutableLiveData<List<Review>> reviewsLiveData =  new MutableLiveData<>();
    private MutableLiveData<List<Movie>> recoMovieLiveData = new MutableLiveData<>();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private String TAG = "MovieRepository";


    @Inject
    public MovieRepository(MovieService movieService) {
        this.movieService = movieService;

    }

    public void requestPopularMovies(int page) {
        Disposable disposable = movieService.getPopularMovies(String.valueOf(page)).subscribeOn(Schedulers.io())
                .subscribe(movieResult -> {
                    if (movieResult != null)
                        popularMoviesLiveData.postValue(movieResult.getMovies());
                });
        compositeDisposable.add(disposable);
    }

    public void requestNowPlayingMovies(int page) {
      Disposable disposable =   movieService.getNowPlayingMovies(String.valueOf(page))
        .subscribeOn(Schedulers.io()).subscribe(movieResult -> {
            if (movieResult == null) return;
            nowPlayingLiveData.postValue(movieResult.getMovies());
        });
        compositeDisposable.add(disposable);
    }

    public void requestHorrorMovies(String genre, int page, String startReleaseDate, int requiredVoteCount) {
        Disposable disposable = movieService.getMoviesByGenre(genre, page, startReleaseDate, requiredVoteCount)
                .subscribeOn(Schedulers.io()).subscribe(movieResult -> {
                    if (movieResult == null) return;
                    horrorMoviesLiveData.postValue(movieResult.getMovies());
                });
        compositeDisposable.add(disposable);
    }

    public void requestDocumentaryMovies(String genre, int page, String startReleaseDate, int requiredVoteCount) {
        Disposable disposable = movieService.getMoviesByGenre(genre, page, startReleaseDate, requiredVoteCount)
                .subscribeOn(Schedulers.io()).subscribe(movieResult -> {
                    if (movieResult == null) return;
                    documentaryLiveData.postValue(movieResult.getMovies());
                });
        compositeDisposable.add(disposable);
    }

    public void requestRomanceMovies(String genre, int page, String startReleaseDate, int requiredVoteCount) {
        Disposable disposable = movieService.getMoviesByGenre(genre, page, startReleaseDate, requiredVoteCount)
                .subscribeOn(Schedulers.io()).subscribe(movieResult -> {
                    if (movieResult == null) return;
                    romanceMoviesLiveData.postValue(movieResult.getMovies());
                });
        compositeDisposable.add(disposable);

    }

    public void requestUpComingMovies(int page) {
        Disposable disposable = movieService.getUpComingMovies(String.valueOf(page))
                .subscribeOn(Schedulers.io()).subscribe(movieResult -> {
                    if (movieResult == null) return;
                    upcomingMoviesLiveData.postValue(movieResult.getMovies());
                });
        compositeDisposable.add(disposable);

    }

    public void requestLatestMovie() {
        Disposable disposable = movieService.getLatestMovie()
                .subscribeOn(Schedulers.io()).subscribe(movie -> {
                    if (movie == null) return;
                    latestMovieLiveData.postValue(movie);
                });
        compositeDisposable.add(disposable);
    }

    public void requestNewPopularMovies(int page) {
        Disposable disposable = movieService.getPopularMovies(String.valueOf(page))
                .subscribeOn(Schedulers.io()).subscribe(movieResult -> {
                    if (movieResult == null) return;
                    requestNewMoviesLiveData.postValue(movieResult.getMovies());
                });
        compositeDisposable.add(disposable);
    }

    public void requestNewMovieByGenre(String genre, int page, String startReleaseDate, int requiredVoteCount) {
        Disposable disposable = movieService.getMoviesByGenre(genre, page, startReleaseDate
                , requiredVoteCount).subscribeOn(Schedulers.io()).subscribe(movieResult -> {
            if (movieResult == null) return;
            requestNewMoviesLiveData.postValue(movieResult.getMovies());
        });
        compositeDisposable.add(disposable);

    }

    public void requestNewUpcomingMovies(int page) {
        Disposable disposable = movieService.getPopularMovies(String.valueOf(page)).subscribeOn(Schedulers.io())
                .subscribe(movieResult -> {
                    if (movieResult == null) return;
                    requestNewMoviesLiveData.postValue(movieResult.getMovies());
                });
        compositeDisposable.add(disposable);
    }

    public void requestVideos(int movieId) {
        Disposable disposable = movieService.getTrailer(movieId).subscribeOn(Schedulers.io())
                .subscribe(trailerResult -> {
                    if (trailerResult == null) return;
                    movieVideosLiveData.postValue(trailerResult.getTrailers());
                });
        compositeDisposable.add(disposable);

    }

    public void requestMovieDetails(int movieId) {
        Disposable disposable = movieService.getMovieDetails(movieId).subscribeOn(Schedulers.io())
                .subscribe(movie -> {
                    if (movie == null) return;
                    movieDetailsLiveData.postValue(movie);
                });
        compositeDisposable.add(disposable);
    }

    public void requestReviews(int movieId) {
        Disposable disposable = movieService.getMovieReviews(movieId).subscribeOn(Schedulers.io())
                .subscribe(reviewResult -> {
                    if (reviewResult == null) return;
                    reviewsLiveData.postValue(reviewResult.getReviews());
                });
        compositeDisposable.add(disposable);
    }

    public void requestRecos(int tvId) {
        Log.d(TAG, "requestRecos: Requesting TV Show ");
        Disposable disposable = movieService.getRecommendations(tvId).subscribeOn(Schedulers.io())
                .subscribe(movieResult -> {
                    if (movieResult == null) return;
                    recoMovieLiveData.postValue(movieResult.getMovies());
                });
        compositeDisposable.add(disposable);
    }

    public LiveData<List<Movie>> getPopularMoviesLiveData() {
        return popularMoviesLiveData;
    }

    public LiveData<List<Movie>> getUpcomingMoviesLiveData() {
        return upcomingMoviesLiveData;
    }

    public LiveData<List<Movie>> getNowPlayingMoviesLiveData() {
        return nowPlayingLiveData;
    }

    public LiveData<Movie> getLatestMovieLiveData() {
        return latestMovieLiveData;
    }

    public LiveData<List<Movie>> getHorrorMoviesLiveData() {
        return horrorMoviesLiveData;
    }

    public LiveData<List<Movie>> getDocumentaryLiveData() {
        return documentaryLiveData;
    }

    public LiveData<List<Movie>> getRomanceMoviesLiveData() {
        return romanceMoviesLiveData;
    }

    public LiveData<List<Trailer>> getMovieTrailerLiveData() {
        return movieVideosLiveData;
    }

    public LiveData<List<Movie>> getRequestedNewMoviesLiveData() {
        return requestNewMoviesLiveData;
    }

    public LiveData<Movie> getMovieDetailsLiveData() {
        return movieDetailsLiveData;
    }

    public LiveData<List<Review>> getReviewsLiveData() {
        return reviewsLiveData;
    }

    public LiveData<List<Movie>> getMovieRecommendations() {
        return recoMovieLiveData;
    }

    public void disposeSubscribers() {
        compositeDisposable.dispose();
    }
}
