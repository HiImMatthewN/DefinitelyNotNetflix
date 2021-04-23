package com.example.pinayflix.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pinayflix.model.datamodel.movie.Movie;
import com.example.pinayflix.model.datamodel.movie.MovieResult;
import com.example.pinayflix.model.datamodel.review.Review;
import com.example.pinayflix.model.datamodel.review.ReviewResult;
import com.example.pinayflix.model.datamodel.trailer.Trailer;
import com.example.pinayflix.model.datamodel.trailer.TrailerResult;
import com.example.pinayflix.network.MovieService;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private MovieService movieService;


    // LiveData For Initialization
    private MutableLiveData<List<Movie>> popularMoviesLiveData;
    private MutableLiveData<List<Movie>> upcomingMoviesLiveData;
    private MutableLiveData<List<Movie>> horrorMoviesLiveData;
    private MutableLiveData<List<Movie>> documentaryLiveData;
    private MutableLiveData<List<Movie>> romanceMoviesLiveData;
    private MutableLiveData<List<Movie>> nowPlayingLiveData;

    private MutableLiveData<Movie> latestMovieLiveData;

    //Video
    private MutableLiveData<List<Trailer>> movieVideosLiveData;

    //LiveData for Updating
    private MutableLiveData<List<Movie>> requestNewMoviesLiveData;

    //LiveData for MovieDetails
    private MutableLiveData<Movie> movieDetailsLiveData;

    //Reviews
    private MutableLiveData<List<Review>> reviewsLiveData;
    private MutableLiveData<List<Movie>> recoMovieLiveData;


    private String TAG = "MovieRepository";

    @Inject
    public MovieRepository(MovieService movieService) {
        popularMoviesLiveData = new MutableLiveData<>();
        upcomingMoviesLiveData = new MutableLiveData<>();
        nowPlayingLiveData = new MutableLiveData<>();
        horrorMoviesLiveData = new MutableLiveData<>();
        documentaryLiveData = new MutableLiveData<>();
        romanceMoviesLiveData = new MutableLiveData<>();
        requestNewMoviesLiveData = new MutableLiveData<>();
        movieVideosLiveData = new MutableLiveData<>();
        latestMovieLiveData = new MutableLiveData<>();
        movieDetailsLiveData = new MutableLiveData<>();
        reviewsLiveData = new MutableLiveData<>();
        recoMovieLiveData = new MutableLiveData<>();
        this.movieService = movieService;


    }


    public void requestPopularMovies(int page) {
        movieService.getPopularMovies(String.valueOf(page))
                .enqueue(new Callback<MovieResult>() {
                    @Override
                    public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            popularMoviesLiveData.postValue(response.body().getMovies());
                            Log.d(TAG, "onResponse: Success");
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResult> call, Throwable t) {
                        Log.d(TAG, "onFailure: Request Failed" + t.getMessage());
                    }
                });

    }

    public void requestNowPlayingMovies(int page) {
        movieService.getNowPlayingMovies(String.valueOf(page))
                .enqueue(new Callback<MovieResult>() {
                    @Override
                    public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            nowPlayingLiveData.postValue(response.body().getMovies());
                            Log.d(TAG, "onResponse: Success");
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResult> call, Throwable t) {
                        Log.d(TAG, "onFailure: Request Failed" + t.getMessage());
                    }
                });

    }

    public void requestHorrorMovies(String genre, int page, String startReleaseDate, int requiredVoteCount) {
        movieService.getMoviesByGenre(genre, page, startReleaseDate, requiredVoteCount).enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                if (response.isSuccessful() && response.body() != null)
                    horrorMoviesLiveData.postValue(response.body().getMovies());

            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                Log.d(TAG, "onFailure: Request Failed" + t.getMessage());
            }
        });

    }

    public void requestDocumentaryMovies(String genre, int page, String startReleaseDate, int requiredVoteCount) {
        movieService.getMoviesByGenre(genre, page, startReleaseDate, requiredVoteCount).enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                if (response.isSuccessful() && response.body() != null)
                    documentaryLiveData.postValue(response.body().getMovies());

            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                Log.d(TAG, "onFailure: Request Failed" + t.getMessage());
            }
        });


    }

    public void requestRomanceMovies(String genre, int page, String startReleaseDate, int requiredVoteCount) {
        movieService.getMoviesByGenre(genre, page, startReleaseDate, requiredVoteCount).enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                if (response.isSuccessful() && response.body() != null)
                    romanceMoviesLiveData.postValue(response.body().getMovies());

            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                Log.d(TAG, "onFailure: Request Failed" + t.getMessage());
            }
        });


    }

    public void requestUpComingMovies(int page) {
        movieService.getUpComingMovies(String.valueOf(page))
                .enqueue(new Callback<MovieResult>() {
                    @Override
                    public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            upcomingMoviesLiveData.postValue(response.body().getMovies());
                            Log.d(TAG, "onResponse: Success");
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResult> call, Throwable t) {
                        Log.d(TAG, "onFailure: Request Failed" + t.getMessage());
                    }
                });


    }

    public void requestLatestMovie() {
        movieService.getLatestMovie()
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            latestMovieLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {

                    }
                });

    }

    public void requestNewPopularMovies(int page) {
        movieService.getPopularMovies(String.valueOf(page))
                .enqueue(new Callback<MovieResult>() {
                    @Override
                    public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            requestNewMoviesLiveData.postValue(response.body().getMovies());
                            Log.d(TAG, "onResponse: Success");
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResult> call, Throwable t) {
                        Log.d(TAG, "onFailure: Request Failed" + t.getMessage());
                    }
                });

    }

    public void requestNewMovieByGenre(String genre, int page, String startReleaseDate, int requiredVoteCount) {
        movieService.getMoviesByGenre(genre, page, startReleaseDate, requiredVoteCount).enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                if (response.isSuccessful() && response.body() != null)
                    requestNewMoviesLiveData.postValue(response.body().getMovies());
                Log.d(TAG, "onResponse: Requested  new movie success");
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                Log.d(TAG, "onFailure: Request Failed" + t.getMessage());
            }
        });


    }

    public void requestNewUpcomingMovies(int page) {
        movieService.getPopularMovies(String.valueOf(page))
                .enqueue(new Callback<MovieResult>() {
                    @Override
                    public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            requestNewMoviesLiveData.postValue(response.body().getMovies());
                            Log.d(TAG, "onResponse: Success");
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResult> call, Throwable t) {
                        Log.d(TAG, "onFailure: Request Failed" + t.getMessage());
                    }
                });

    }

    public void requestVideos(int movieId) {
        movieService.getTrailer(movieId).enqueue(new Callback<TrailerResult>() {
            @Override
            public void onResponse(Call<TrailerResult> call, Response<TrailerResult> response) {
                if (response.isSuccessful() && response.body() != null) {
                    movieVideosLiveData.postValue(response.body().getTrailers());
                    Log.d(TAG, "onResponse: Video Retrieve Success");

                }
            }

            @Override
            public void onFailure(Call<TrailerResult> call, Throwable t) {
                Log.d(TAG, "onFailure: Request Failed" + t.getMessage());

            }
        });

    }

    public void requestMovieDetails(int movieId) {
        movieService.getMovieDetails(movieId).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "onResponse: Movie Details Success");
                    movieDetailsLiveData.postValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
    }
    public void requestReviews(int movieId){
        movieService.getMovieReviews(movieId).enqueue(new Callback<ReviewResult>() {
            @Override
            public void onResponse(Call<ReviewResult> call, Response<ReviewResult> response) {
                if(response.isSuccessful() && response.body() != null){
                    reviewsLiveData.postValue(response.body().getReviews());
                    Log.d(TAG, "onResponse: Requesting Reviews success");
                }
            }

            @Override
            public void onFailure(Call<ReviewResult> call, Throwable t) {
                Log.d(TAG, "onFailure: Requesting Reviews failed" + t.getMessage());
            }
        });
    }
    public void requestRecos(int tvId){
        Log.d(TAG, "requestRecos: Requesting TV Show ");
        movieService.getRecommendations(tvId).enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                if (response.isSuccessful() && response.body() != null) {
                    recoMovieLiveData.postValue(response.body().getMovies());
                    Log.d(TAG, "onResponse: GET Movie Recommendations success");
                }
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                Log.d(TAG, "Getting TV Show Recommendations failed: " + t.getMessage());
            }
        });

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
    public LiveData<List<Review>> getReviewsLiveData(){
        return reviewsLiveData;
    }
    public LiveData<List<Movie>> getMovieRecommendations(){
        return recoMovieLiveData;
    }
}
