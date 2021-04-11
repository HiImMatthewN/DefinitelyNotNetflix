package com.example.pinayflix.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pinayflix.model.datamodel.movie.Movie;
import com.example.pinayflix.model.datamodel.movie.MovieDetails;
import com.example.pinayflix.model.datamodel.movie.MovieResult;
import com.example.pinayflix.model.datamodel.review.Review;
import com.example.pinayflix.model.datamodel.review.ReviewResult;
import com.example.pinayflix.model.datamodel.Video;
import com.example.pinayflix.model.datamodel.VideoResult;
import com.example.pinayflix.network.MovieService;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {
    private final String API_KEY = "1a9be3fd53844f172e7fb74a75a6c5fd";
    private final String BASE_URL = "https://api.themoviedb.org/3/";
    private Retrofit retrofit;
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
    private MutableLiveData<List<Video>> movieVideosLiveData;

    //LiveData for Updating
    private MutableLiveData<List<Movie>> requestNewMoviesLiveData;

    //LiveData for MovieDetails
    private MutableLiveData<MovieDetails> movieDetailsLiveData;

    //Reviews
    private MutableLiveData<List<Review>> reviewsLiveData;


    private String TAG = "MovieRepository";

    public MovieRepository() {
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

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(this::addApiKeyToRequests)
                .build();
        retrofit = new Retrofit.Builder()
                .client(client)

                .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();
        movieService = retrofit.create(MovieService.class);

    }

    private okhttp3.Response addApiKeyToRequests(Interceptor.Chain chain) {
        Request.Builder request = chain.request().newBuilder();
        HttpUrl originalHttp = chain.request().url();
        HttpUrl newUrl = originalHttp.newBuilder().addQueryParameter("api_key", API_KEY).build();
        request.url(newUrl);
        try {
            return chain.proceed(request.build());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
        movieService.getVideos(movieId).enqueue(new Callback<VideoResult>() {
            @Override
            public void onResponse(Call<VideoResult> call, Response<VideoResult> response) {
                if (response.isSuccessful() && response.body() != null) {
                    movieVideosLiveData.postValue(response.body().getVideos());
                    Log.d(TAG, "onResponse: Video Retrieve Success");

                }
            }

            @Override
            public void onFailure(Call<VideoResult> call, Throwable t) {
                Log.d(TAG, "onFailure: Request Failed" + t.getMessage());

            }
        });

    }

    public void requestMovieDetails(int movieId) {
        movieService.getMovieDetails(movieId).enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "onResponse: Movie Details Success");
                    movieDetailsLiveData.postValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {

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

    public LiveData<List<Video>> getMovieVideos() {
        return movieVideosLiveData;
    }

    public LiveData<List<Movie>> getRequestedMovies() {
        return requestNewMoviesLiveData;
    }

    public LiveData<MovieDetails> getMovieDetailsLiveData() {
        return movieDetailsLiveData;
    }
    public LiveData<List<Review>> getReviewsLiveData(){
        return reviewsLiveData;
    }
}
